Ext.define('ShopinDesktop.GuideCreastChestcartNumWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel', 'Ext.data.JsonStore' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	dataStore : null,
	sid :null,
	guideNo :null,
	shopName :null,
	name :null,
	validBit :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initCmps(me);
        sid = config.sid;
        guideNo = config.guideNo;
        name = config.name;
        validBit = config.validBit;
        
		this.superclass.constructor.call(this, {
			title :'导购生成胸卡编号',
			height : 310,
			width  : 350,
			layout:"fit",
			modal: true,
			maximizable:true,
			constrain : true,
			plain : true, 
			items :  [me.gridPanel],
			buttons :[
				{
					text : '关闭',
					handler : function() {
						me.close();
					}
				}
//				,{
//					text : '取消',
//					handler : function() {
//						me.close();
//					}
//				}
			]
		});
	},
	initCmps : function(me) {
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;
		
		//修改可编辑表格显示文字为中文，否则为英文
		if (Ext.grid.RowEditor) {
			Ext.apply(Ext.grid.RowEditor.prototype, {
				saveBtnText: '保存',
				cancelBtnText: '取消',
				errorsText: '错误信息',
				dirtyText: '已修改,你需要提交或取消变更'
			});
		}
		
		this.store = Ext.create('Ext.data.JsonStore', {
			autoLoad : false,
			clearOnPageLoad : true,
			fields : [
				"sid","guideNo","shopName","shopId","chestcardNumber"
			],
			pageSize : 15,
			storeId : 'guideChestnumStoreId',
			proxy : {
					type : 'ajax',
					api : {
						read : _appctx + '/guideLogininfo/getGuideChestCartList.json?guideNo='+guideNo
//						,
//						update: _appctx + '/guideLogininfo/updateLogininfo.json',
					},
					idParam : 'sid',
					reader : {
						type: "json",
						root : "obj.list",
						totalProperty :"obj.total"
					},
					writer : {
						type : 'json',
						encode : true,
						root : 'data',
						expandData : true
					}
				}
		});
	   this.store.loadPage(1);
	   
       function delGuideChestCard(sid){
			Ext.Ajax.request({
				url : _appctx + '/guideLogininfo/delGuideChestCard',
				method:'POST',
				params: { sid: sid},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功');
						Ext.getCmp('guideChestCardGridId').getStore().load();
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
	    		id : 'guideChestCardGridId',
	    		autoScroll:true,
				autoWidth:true,
				manageHeight:true,
				plugins: [
			          Ext.create('Ext.grid.plugin.RowEditing', {
					        clicksToMoveEditor: 1,
					        autoCancel: true
					    })
				      ],
				columns:[
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'导购编号',dataIndex:'guideNo',width:60,hidden : true,sortable:true},
					{header:'门店名称',dataIndex:'shopName',width:80,sortable:true
//						,
//						 editor: {
//			                xtype: 'combo',
//			                allowBlank: false,
//			                store:new Ext.data.JsonStore({
//								autoLoad:true,
//								proxy : {
//									type : 'ajax',
//									api : {
//										read : _appctx + '/guideSupply/getShopList',
//									},
//									idParam : 'sid',
//									reader : {
//										type : 'json',
//										root : 'result'
//									}
//								},
//								fields: ["sid", "shopName"]
//							}),
//							valueField: 'sid',
//							displayField:'shopName',
//							hiddenName:'sid',
//							triggerAction : 'all',
//							name:'shop',
//							mode:'local',
//							allowBlank : false
//			            }
					},
					{header:'胸卡编号',dataIndex:'chestcardNumber',width:100,sortable:true,
						editor: {
			                 xtype:'textfield',
			                 allowBlank: false
				        }
					},
					{
						text:"删除",
						xtype:'actioncolumn',
						sortable: true,
						width:60,
						items :[
							{
								text : '删除',
								xtype : 'button',
								tooltip: '删除',
								icon:_appctx+'/images/remove.gif',
								handler:function(grid, rowIndex, colIndex){
									var record = grid.getStore().getAt(rowIndex);  
									Ext.Msg.confirm("提示","确认要删除此胸卡编号？",function(button){
										if(button=="yes"){
											sid = record.data.sid;
											delGuideChestCard(sid);
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
						if(0 == validBit){
							Ext.Msg.alert("提示","此导购已是无效状态，不能生成新的胸卡编号！");
						    return;
						}
						Ext.Msg.confirm("提示","确认为导购 "+name+" 生成 "+shopname+" 的胸卡编号？",function(button){
							
							if(button=="yes"){
								
								Ext.Ajax.request({
									url : _appctx + '/guideLogininfo/checkGuideLogininfo',
									method:'POST',
									params: { 
										guideNo: guideNo,
										shopId: shopid
								    },
									success: function(response){
											var result = Ext.decode(response.responseText);
											if(result.success=="true" && result.obj != undefined){
												var chestcardNumber = result.obj.chestcardShoprule + result.obj.chestcardNum;
												shopName = result.obj.shopName;
												
												Ext.MessageBox.alert('提示',"导购："+name+"  在门店："+shopName+" 已有胸卡编号为："+chestcardNumber+",无需再次生成！");
											}else{
																		
												Ext.Ajax.request({
													url: _appctx + '/guideLogininfo/saveLogininfo',
													method:'POST',
													params: { 
														shopId: shopid,
														shopName: shopname,
														guideNo: guideNo,
														username : username
													},
													success: function(response,opt){
														
														var response = Ext.JSON.decode(response.responseText);
											    		if("true"== response.success){
											    			Ext.MessageBox.alert('提示', "生成编号成功");
											    			me.store.reload();
											    			Ext.getCmp("guideInfoGridPanel").store.reload();
											    		}else{
											    			Ext.MessageBox.alert('提示', response.memo);
											    		}
													},
													failure: function(){
														Ext.Msg.alert("错误","生成编号失败！")
													}
												})
										}
									},
									failure: function(){
										Ext.Msg.alert('','访问失败，请与管理员联系');
									}
								})
							}
						})
					}
				}],
				store:this.store,  
				columnLines : true,
				selModel: this.sm,
				sortableColumns : false,
				listeners : {
					edit : function(editor, context, eOpts) {
						Ext.Ajax.request({
							url : _appctx + '/guideLogininfo/updateLogininfo.json',
							method:'POST',
							params: { 
								sid: context.record.data.sid,
								shopId: context.record.data.shopId,
								chestcardNumber: context.record.data.chestcardNumber,
								guideNo : context.record.data.guideNo,
								username : username
							},
							success: function(response){
								var result = Ext.decode(response.responseText);
								if(result.success=="true"){
									Ext.Msg.alert('提示','修改胸卡编号成功');
									Ext.getCmp('guideInfoGridPanel').getStore().load();
								}else{
									if(result.errorCode == "100000"){//此胸卡编号已被使用，请重新修改！
										Ext.Msg.alert('错误',result.memo);
									}else{
										Ext.Msg.alert('错误','修改胸卡编号失败！');
									}
								}
							},
							failure: function(){
								Ext.Msg.alert('','失败，请与管理员联系');
							}
						});
					},
					canceledit : function(editor, context, eOpts) {
						me.store.reload();
					}
				}
			});
	    
	    me.gridPanel.render(document.body);
		window.onresize=function(){
		        me.gridPanel.setWidth(document.documentElement.clientWidth);
		        me.gridPanel.setHeight(document.documentElement.clientHeight-40);
		    };
		},
});
