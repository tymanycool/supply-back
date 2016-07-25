Ext.define('ShopinDesktop.GuideBoundSupplyWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	dataStore : null,
	sid :null,
	guideNo :null,
	validBit :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initCmps(me);
        sid = config.sid;
        guideNo = config.guideNo;
        validBit = config.validBit;
		this.superclass.constructor.call(this, {
			title :'导购绑定供应商信息',
			id:'guideSupplyId',
			height : 510,
			width  : 950,
			constrain : true,
			maximizable: true,
			layout:"fit",
			modal: true,
			plain : true, 
			items :  [me.gridPanel ],
			buttons :[
//				{
//					text : '确定',
//					handler : function() {
//						me.close();
//					}
//				},
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
				"sid","guideNo","supplyId","supplyName","supplyId","shopName","brand","categroys","validBit",
				"changeSupplyName","changeBrand","changeCategroys"
			],
			pageSize : 15,
			storeId : 'guideSupplyStoreId',
			proxy : {
					type : 'ajax',
					api : {
						read : _appctx + '/guideSupply/list?guideNo='+guideNo,
					},
					idParam : 'sid',
					reader : {
						type: "json",
						root : "obj.list",
						totalProperty :"obj.total"
					}
				}
		});
//	   this.store.loadPage(1);
       
       function delGuideSupplyInfo(sid){
			Ext.Ajax.request({
					url : _appctx + '/guideSupply/delGuideSupply',
					method:'POST',
					params: { 
						sid: sid,
						username : username
					},
					success: function(response){
						var result = Ext.decode(response.responseText);
						if(result.success=="true"){
							Ext.Msg.alert('','删除成功');
							Ext.getCmp('guideBoundSupplyGridId').getStore().load();
							Ext.getCmp("guideInfoGridPanel").store.reload();
						}else{
							Ext.Msg.alert('错误','删除失败！');
						}
					},
					failure: function(){
						Ext.Msg.alert('','删除失败，请与管理员联系');
					}
				})
		 }
       
       //品牌下拉列表
       var brandCombo= new Ext.data.JsonStore({
			autoLoad:true,
			proxy : {
				type : 'ajax',
				api : {
					read : _appctx + '/guideSupply/getBrandInfo',
				},
				reader : {
					type: 'json',
						root: 'result'
				}
			},
			fields : ["brandName","brandSid","sid"]
		});
       
	    this.gridPanel = Ext.create('Ext.grid.Panel', {
	    		id : 'guideBoundSupplyGridId',
	    		autoScroll:true,
				autoWidth:true,
				manageHeight:true,
				columns:[
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'导购编号',dataIndex:'guideNo',width:60,hidden : true,sortable:true},
					{header:'门店名称',dataIndex:'shopName',width:80,sortable:true},
					{header:'供应商编码',dataIndex:'supplyId',width:100,sortable:true},
					{header:'供应商名称',dataIndex:'supplyName',width:180,sortable:true},
					{header:'品牌名称',dataIndex:'brand',width:120,sortable:true},
					{header:'品类名称',dataIndex:'categroys',width:120,sortable:true},
					{header:'是否有效',dataIndex:'validBit',width:60,sortable:true,
						renderer:function(value){
							if(value == 0) {
								return '无效';
							}
							if(value == 1) {
								return '有效';
							}
						}
					},
//					{header:'转场后供应商名称',dataIndex:'changeSupplyName',width:150,sortable:true},
//					{header:'转场后品牌名称',dataIndex:'changeBrand',width:120,sortable:true},
//					{header:'转场后品类名称',dataIndex:'changeCategroys',width:120,sortable:true},
					{
						text:"删除",
						xtype:'actioncolumn',
						sortable: true,
						width:60,
						align:'center',
						items :[
							{
							text : '删除',
							xtype : 'button',
							tooltip: '删除',
							icon:_appctx+'/images/remove.gif',
							handler:function(grid, rowIndex, colIndex){
								var record = grid.getStore().getAt(rowIndex);  
								Ext.Msg.confirm("提示","确认要解除该导购与此供应商信息？",function(button){
									if(button=="yes"){
										sid = record.data.sid;
										delGuideSupplyInfo(sid);
									}
								});
								}
							}
						]	
					},
					{
						text:"转场",
						xtype:'actioncolumn',
						sortable: true,
						width:60,
						align:'center',
						items :[
							{
							text : '转场',
							xtype : 'button',
							tooltip: '转场',
							icon:_appctx+'/images/refresh.png',
							handler:function(grid, rowIndex, colIndex){
								var record = grid.getStore().getAt(rowIndex);  
								sid = record.data.sid;
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
												id:"changeSupplyName",
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
												displayField:"supplyinfoName",
												hiddenName:'supplyinfoSid',
												triggerAction : 'all',
												name:'supplyinfoName',
												queryMode: "local",
												typeAhead: true,
												allowBlank : false,
												listeners: {
				                                    change: function(combo, nv, ov){
				                                        if(nv!=ov){
					                                        var brandCombo = Ext.getCmp("changeBrandName");
					                                        var supplyId = Ext.getCmp("changeSupplyName").getValue();
					                                        brandCombo.clearValue();
					                                        var brandStore=brandCombo.getStore();
										
	       													brandStore.load({
	       														params: {
									                        		shopId: shopid,
									                        		supplySid: supplyId
									                        	}
									                    	});
				                                      }
				                                    }, beforequery : function(e){
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
													fieldLabel : '品牌',
													width:300,
													id:"changeBrandName",
													multiSelect: true,											
													emptyText: '请选择...',
													store: brandCombo,
													valueField: 'brandSid',
													displayField:'brandName',
													hiddenName:'brandSid',
													triggerAction : 'all',
													name:'brandName',
													mode:'local',
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
					              	  },{
					                	xtype: 'container',
					                    layout: 'hbox',
					                    height:5
					                  }
					                ]
								});
								win = Ext.create('Ext.window.Window', {
									title:"转场",
									height : 310,
									width  : 420,
									id:'changeGuideSupplyWindowId',
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
										
											var changeSupplyName = Ext.getCmp("changeSupplyName").getValue();
											var changeSupplyNames = Ext.getCmp("changeSupplyName").getRawValue();
											var supplyNameArr = changeSupplyNames.split(" ");
											var supplyName = supplyNameArr[1];
											
											var changeBrandName = Ext.getCmp("changeBrandName").getRawValue();
											var changeBrandSid = Ext.getCmp("changeBrandName").getValue();//品牌在erp中sid
											var changeBrandSsdSid = brandCombo.getAt(0).get("sid");//品牌在ssd中sid
											var brandSsdSid = brandCombo.getAt(0).get("sid");//品牌在ssd中sid
											
											Ext.Ajax.request({
												url: _appctx + '/guideSupply/saveGuideSupply?change=0&brandId='+changeBrandSid,
												method:'POST',
												params: { 
													shopId: shopid,
													shopName: shopname,
													supplyName: supplyName,
													supplyId : changeSupplyName,
													brandName : changeBrandName,
													brandSsdSid : brandSsdSid,
													changeBrandSsdSid : changeBrandSsdSid,
													sid : sid,
													guideNo: guideNo,
													username : username
												},
												success: function(){
													Ext.getCmp('guideBoundSupplyGridId').store.reload();
													Ext.getCmp("guideInfoGridPanel").store.reload();
												},
												failure: function(){
													Ext.Msg.alert("错误","访问失败！")
												}
											})
										   Ext.getCmp('changeGuideSupplyWindowId').close();
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
							Ext.Msg.alert("提示","此导购已是无效状态，不能绑定供应商！");
						    return;
						}
						
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
//											 {
//												xtype:"combo",
//												fieldLabel : '门店',
//												width:300,
//												id:"shop",
//												emptyText: '请选择...',
//												store:new Ext.data.JsonStore({
//													autoLoad:true,
//													proxy : {
//														type : 'ajax',
//														api : {
//															read : _appctx + '/guideSupply/getShopList',
//														},
//														idParam : 'sid',
//														reader : {
//															type : 'json',
//															root : 'result'
//														}
//													},
//													fields: ["sid", "shopName"]
//													
//												}),
//												valueField: 'sid',
//												displayField:'shopName',
//												hiddenName:'sid',
//												triggerAction : 'all',
//												name:'shop',
//												mode:'local',
//												allowBlank : false,
//												value : shopname
////												,
////												listeners: {
////				                                    change: function(combo, nv, ov){
////				                                        if(nv!=ov){
////					                                        var supplyCombo = Ext.getCmp("supplyinfoName");
////					                                        var shopId = Ext.getCmp("shop").getValue();
////					                                        supplyCombo.clearValue();
////					                                        var supplyStore=supplyCombo.getStore();
////					                                        
////           													supplyStore.load({
////           														params: {
////									                        		shopId: shopId
////									                        	}
////									                    	});
////				                                      }
////				                                    }, beforequery : function(e){
////									                    var combo = e.combo;
////										                if(!e.forceAll){
////											                var value = e.query;
////											                combo.store.filterBy(function(record,id){
////												                var text = record.get(combo.displayField);
////												                return (text.indexOf(value)!=-1);
////											                });
////											                combo.expand();
////											                return false;
////										                }
////									               }
////				                                }
//											}
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
											displayField:"supplyinfoName",
											hiddenName:'supplyinfoSid',
											triggerAction : 'all',
											name:'supplyinfoName',
											queryMode: "local",
											typeAhead: true,
											allowBlank : false,
											listeners: {
			                                    change: function(combo, nv, ov){
			                                        if(nv!=ov){
				                                        var brandCombo = Ext.getCmp("brandName");
				                                        var supplyId = Ext.getCmp("supplyinfoName").getValue();
				                                        brandCombo.clearValue();
				                                        var brandStore=brandCombo.getStore();
				                                        
									
       													brandStore.load({
       														params: {
								                        		shopId: shopid,
								                        		supplySid: supplyId
								                        	}
								                    	});
			                                      }
			                                    }, beforequery : function(e){
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
											fieldLabel : '品牌',
											width:300,
											id:"brandName",
											multiSelect: true,											
											emptyText: '请选择...',
											store:brandCombo,
											valueField: 'brandSid',
											displayField:'brandName',
											hiddenName:'brandSid',
											triggerAction : 'all',
											name:'brandName',
											mode:'local',
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
			              	  },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                  }
//			              	  ,{
//			                    xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//										 {
//											xtype:"combo",
//											fieldLabel : '一级品类',
//											width:300,
//											id:"name",
//											emptyText: '请选择...',
//											store:new Ext.data.JsonStore({
//												autoLoad:true,
//												proxy : {
//													type : 'ajax',
//													api : {
//														read : _appctx + '/guideSupply/getFlCategory',
//													},
//													reader : {
//														type: 'json',
//		             									root: 'list'
//													}
//												},
//												fields : ["id","name"]
//											}),
//											valueField: 'name',
//											displayField:'name',
//											hiddenName:'name',
//											triggerAction : 'all',
//											name:'name',
//											mode:'local',
//											allowBlank : false,
//											listeners: {
//			                                   beforequery : function(e){
//									                    var combo = e.combo;
//										                if(!e.forceAll){
//											                var value = e.query;
//											                combo.store.filterBy(function(record,id){
//												                var text = record.get(combo.displayField);
//												                return (text.indexOf(value)!=-1);
//											                });
//											                combo.expand();
//											                return false;
//										                }
//									               }
//			                                }
//										}
//				                    ]
//			              	  }
			                ]
						});
						win = Ext.create('Ext.window.Window', {
							title:"添加",
							height : 310,
							width  : 420,
							id:'addGuideSupplyWindowId',
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
								
									var supplySid = Ext.getCmp("supplyinfoName").getValue();
									var supplyNames = Ext.getCmp("supplyinfoName").getRawValue();
									var supplyNameArr = supplyNames.split(" ");
									var supplyName = supplyNameArr[1];
									
									var brandName = Ext.getCmp("brandName").getRawValue();
									var brandSid = Ext.getCmp("brandName").getValue();//品牌在erp中sid
									var brandSsdSid = brandCombo.getAt(0).get("sid");//品牌在ssd中sid
									
//									var text = Ext.getCmp("text").getValue();
									Ext.Ajax.request({
										url: _appctx + '/guideSupply/saveGuideSupply?brandId='+brandSid,
										method:'POST',
										params: { 
											shopId: shopid,
											shopName: shopname,
											supplyName: supplyName,
											supplyId : supplySid,
											brandName : brandName,
											brandSsdSid : brandSsdSid,
											guideNo: guideNo,
											username : username
										},
										success: function(){
											Ext.getCmp('guideBoundSupplyGridId').store.reload();
											Ext.getCmp("guideInfoGridPanel").store.reload();
										},
										failure: function(){
											Ext.Msg.alert("错误","访问失败！")
										}
									})
								   Ext.getCmp('addGuideSupplyWindowId').close();
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
		},
});
