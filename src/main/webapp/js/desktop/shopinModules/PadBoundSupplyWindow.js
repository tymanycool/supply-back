/*
 * 弃用，syk
 */
Ext.define('ShopinDesktop.PadBoundSupplyWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	dataStore : null,
	sid :null,
	padNo :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initCmps(me);
        sid = config.sid;
        padNo = config.padNo;
		this.superclass.constructor.call(this, {
			title :'Pad绑定供应商信息-弃用',
			id:'padSupplyId',
			height : 350,
			width  : 480,
			constrain : true,
			plain : true, 
			layout:"fit",
			modal: true,
			maximizable:true,
			items :  [ me.gridPanel],
			buttons :[
				{
					text : '关闭',
					handler : function() {
						me.close();
					}
				}
			]
		});
	},
	initCmps : function(me) {
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;
		
		var isok=true;
		this.store = Ext.create('Ext.data.JsonStore', {
			autoLoad : true,
			clearOnPageLoad : true,
			fields : [
				"sid","padNo","supplyName","supplyId","shopName"
			],
			proxy : {
					type : 'ajax',
					api : {
						read : _appctx + '/padSupply/list?padNo='+padNo,
					},
					idParam : 'sid',
					reader : {
						type: "json",
						root : "obj.list"
					}
				}
		});
       
	   /**
	    * 解除pad与供应商关系
	    * @param {Object} sid
	    */
	   function delPadSupplyInfo(sid){
			Ext.Ajax.request({
				url : _appctx + '/padSupply/delPadSupplyInfo',
				method:'POST',
				params: { 
					sid: sid,
					username : username
				},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功');
						Ext.getCmp('padBoundSupplyGridId').getStore().load();
						Ext.getCmp('padBaseinfoGridPanel').store.reload();
					}else{
						Ext.Msg.alert('错误','删除失败！');
					}
				},
				failure: function(){
					Ext.Msg.alert('','删除失败，请与管理员联系');
				}
			})
		 }
	   
	   
	   
	    this.gridPanel = Ext.create('Ext.grid.Panel', {
	    		id : 'padBoundSupplyGridId',
				autoScroll:true,
				autoWidth:true,
				manageHeight:true,
				columns:[
				        new Ext.grid.RowNumberer(),
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'PAD编号',dataIndex:'padNo',width:60,hidden : true,sortable:true},
					{header:'门店名称',dataIndex:'shopName',width:80,sortable:true},
					{header:'供应商名称',dataIndex:'supplyName',width:230,sortable:true},
					{
						text:"操作",
						xtype:'actioncolumn',
						sortable: true,
						width:80,
						align:'center',
						items :[
							{
							text : '删除',
							xtype : 'button',
							tooltip: '删除',
							icon:_appctx+'/images/remove.gif',
							handler:function(grid, rowIndex, colIndex){
								var record = grid.getStore().getAt(rowIndex);
								Ext.Msg.confirm("提示","确认要解除该PAD与此供应商绑定信息？",function(button){
									if(button=="yes"){
										sid = record.data.sid;
										delPadSupplyInfo(sid);
									}
								});
								}
							}
						]	
					}
				],
				tbar:[
					{
					xtype : 'button',
					text : '添加',
					handler : function(button, e) {
						 Ext.Ajax.request({
							url : _appctx + '/padSupply/checkPadBoundSupply',
							method:'POST',
							params: { 
								padNo: padNo
						    },
							success: function(response){
								var result = Ext.decode(response.responseText);
								if(result.success=="true" && result.obj != undefined){
									var padNum = result.obj.padNo;
									var supplyName = result.obj.supplyName;
									var shopName = result.obj.shopName;
									
									Ext.Msg.alert('提示',"PAD编号："+padNum+"  已绑定门店："+shopName+" 供应商："+supplyName+" 如需重新绑定请先解绑与该供应商关系！");
								}else if(result.success=="false"){
									var memo = result.memo;
									Ext.Msg.alert('提示',memo);
								}else{
															
									var win = null;
									var form = Ext.create('Ext.form.Panel', {
										height : 310,
										width  : 420,
										layout : 'anchor',
										bodyPadding: '15 15 15 15',
										defaults : {
											anchor : '100%',
											labelAlign : 'right'
										},
										items: [ 
												{
								                    xtype: 'container',
								                    layout: 'hbox',
								                    items: [
														 {
							                    			xtype: 'textfield',
															name : 'shopname',
															width : 300,
															fieldLabel : '门店',
															readOnly : true,
															value: shopname
														}
													]
										},{
						                	xtype: 'container',
						                    layout: 'hbox',
						                    height:5
						                },{
						                    xtype: 'container',
						                    layout: 'hbox',
						                    items: [
													 {
														xtype:"combo",
														fieldLabel : '供应商',
														width:300,
														id:"supplyinfoName",
														emptyText: '请选择...',
														store:new Ext.data.Store({
												        	autoLoad:true,
															proxy : {
																type : 'ajax',
																api : {
																	read : _appctx + '/guideSupply/getSupplyInfo?shopId='+shopid,
																},
																reader : {
																	type: 'json',
												 					root: 'list'
																}
															},
															fields : ["supplyinfoName","supplyinfoSid"]
												    	}),
														valueField: 'supplyinfoSid',
														displayField:'supplyinfoName',
														hiddenName:'supplyinfoSid',
														triggerAction : 'all',
														name:'supplyinfoName',
														mode:'local',
														typeAhead: true,
														allowBlank : false,
														listeners: {
						                                   beforequery : function(e){
												                    var combo = e.combo;
													                if(!e.forceAll){
														                var value = e.query;
														                combo.store.filterBy(function(record,id){
															                var text = record.get(combo.displayField);
															                return (text.indexOf(value)!=-1);
														                });
														                combo.expand();
														                return false;
													                }
												               }
						                                }
													 }
							                    ]
						              	  }
						                ]
									});
									win = Ext.create('Ext.window.Window', {
										title:"添加",
										height : 310,
										width  : 420,
										id:'addPadSupplyWindowId',
										plain : true,
										resizable :true,
										items : [ form ],
										buttons : [ {
											text : '重置',
											handler : function() {
											form.getForm().reset();
											}
										}, {
											text : '确定',
											handler : function() {
												var supplyName = Ext.getCmp("supplyinfoName").getRawValue();
												var supplySid = Ext.getCmp("supplyinfoName").getValue();
												Ext.Ajax.request({
													url: _appctx + '/padSupply/savePadSupply',
													method:'POST',
													params: { 
														shopId: shopid,
														shopName: shopname,
														supplyName: supplyName,
														supplyId : supplySid,
														padNo: padNo,
														username : username,
														userSid : userSid
													},
													success: function(){
														
														Ext.Msg.alert("提示","操作成功！")
														Ext.getCmp('padBoundSupplyGridId').store.reload();
														Ext.getCmp('padBaseinfoGridPanel').store.reload();
													},
													failure: function(){
														Ext.Msg.alert("提示","操作失败！")
													}
												})
											   Ext.getCmp('addPadSupplyWindowId').close();
											}
										}, {
											text : '取消',
											handler : function() {
												win.close();
											}
										} ]
									});
									Ext.getCmp(me.id).add(win);
						win.show();
				}
				},
				failure: function(){
					Ext.Msg.alert('','，请与管理员联系');
				}
			})
					}
				}],
				store:this.store,  
				columnLines : true,
				selModel: this.sm,
				sortableColumns : false
				});
	    
	    me.gridPanel.render(document.body);
		window.onresize=function(){
		        me.gridPanel.setWidth(document.documentElement.clientWidth);
		        me.gridPanel.setHeight(document.documentElement.clientHeight-40);
		    };
		}
});
