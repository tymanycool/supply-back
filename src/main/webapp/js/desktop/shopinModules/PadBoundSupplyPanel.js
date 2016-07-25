/**
 * 绑定功能-新系统
 */
Ext.define('ShopinDesktop.PadBoundSupplyPanel', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	dataStore : null,
	sid :null,
	padNo:null,
	record :null,
	useTypeNew:null,
	useTypeDescNew:null,
    padStatus:null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		record=config.record;
        sid = config.record.data.sid;
        padNo = config.record.data.padNo;
        this.initCmps(me);
		this.superclass.constructor.call(this, {
			title :'Pad绑定供应商信息',
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
						read : _appctx + '/padSupplyRelation/list?padNo='+padNo,
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
		    var supplycount = Ext.getCmp('padBoundSupplyGridId').store.getCount();
			var flag="this_is_a_flag";
			if(supplycount==1){
				flag="";
			}
			Ext.Ajax.request({
				url : _appctx + '/padSupplyRelation/delPadSupplyInfo',
				method:'POST',
				params: { 
					sid: sid,
					userSid:userSid,
					username : username,
					flag:flag
				},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功');
						Ext.getCmp('padBoundSupplyGridId').getStore().load();
						Ext.getCmp('padBaseinfoGridPanelOfPCV').store.reload();
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
					{header:'供应商Id',dataIndex:'supplyId',width:230,sortable:true},
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
						//统计sotre.getCount()和使用类型是否符合，提示解绑或者修改使用类型;
						var supplycount = Ext.getCmp('padBoundSupplyGridId').store.getCount();
						
						//获取padBaseInfo（从上一个页面获取，数据不够准确，不实时）
						Ext.Ajax.request({
						    url:  _appctx+'/padBaseinfo/selectPadByPadNo',
						    params: {
						        padNo:padNo
						    },
						    success: function(response){
						    	 var padBaseInfo= Ext.JSON.decode(response.responseText).obj;
						    	 record = padBaseInfo;
						    	 padStatus = padBaseInfo.padStatus;
						    	 useTypeNew =padBaseInfo.useType;
						    	 useTypeDescNew = padBaseInfo.useTypeDesc;
						    	 //只有卖场（使用类型为 大场，内衣功能区，导购，不弹出窗口）
						    	 if(supplycount > 1){
										if(useTypeNew === 0 || useTypeNew === 2 || useTypeNew === 3){
											Ext.Msg.alert('提示','此PAD使用类型为：<span style="font-size:20px;">'+useTypeDescNew+'</span> ,只能关联一个供应商。<br> 注意：使用类型为<span style="font-size:20px;">‘主管’</span>的PAD才可以关联多个供应商。<br>请先<span style="font-size:20px;">解绑</span>。或者双击表格此行,点击<span style="font-size:20px;">‘修改’</span>选项修改使用类型');
											return;
										}
									}
						    	 if(useTypeNew === null || useTypeNew === undefined || useTypeNew === -1 || useTypeNew === "" || useTypeNew === 1 ){ 
										Ext.create("ShopinDesktop.PadBoundSupplyAddView",{
											record : me.record
										}).show();
									}else if(useTypeNew === 0 || useTypeNew === 2 || useTypeNew === 3){  //  0:导购，2：内衣功能区，3： 大场
										
										Ext.Msg.alert('提示',' 此PAD编号为： '+padNo+'。 使用类型为:  '+useTypeDescNew+'。<br> 注意：使用类型为<span style="font-size:20px;">‘主管’</span>的PAD才可以关联多个供应商。      请双击表格此行点击<span style="font-size:20px;">‘修改’</span>选项修改使用类型');
									}
						    }
						});
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
