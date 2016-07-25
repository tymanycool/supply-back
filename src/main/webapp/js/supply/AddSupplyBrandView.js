Ext.define("Supply.AddSupplyBrandView",{
//	extend:"Ext.grid.Panel",
	extend: "Ext.window.Window",
	id :"AddSupplyBrandView3",
	requires: [
		'Supply.SupplyInforView'
	],
	baseUrl: _appctx,
	tbar:null,
	supplyStore:null,
	brandStore :null,
	categroy1Store : null,
	AddSupplyBrandView3 : null,
	
	itemsPerPage : 20,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
//			columns: this.columns,
			tbar: this.tbar,
			bbar : this.bBar,
//			store: this.messageStore,
//			store: this.supplyStore,
			
			
			items : [ this.formpanel ],
			modal : true,
			height : 400,
			width : 500,
			title : "添加品牌",
			
			
			viewConfig : {
				enableTextSelection : true
			// 启用文字选择
			}
		});
	},
	initComponents: function() {
//		var thisView = this;
		var thisWindow = this;
		var baseUrl = this.baseUrl;
		
		var supplyStore = Ext.create("Ext.data.Store",{
			autoLoad:true,
		//	pageSize : this.itemsPerPage,
			
			fields : [
					"sid","supplyName"
					],
 			proxy: {
						type: "ajax",
						url : _appctx + "/supply/selectSupplyBySupplySid.json",
						getMethod: function(){
							return 'POST';
						},
						reader: {
							type: "json",
							root : "page.list",
//							totalProperty :"total"
						},
						listeners: {
							exception: function(proxy, response, operation, eOpts) {
								if (response.status != 200) {
									Ext.MessageBox.alert("Error", "加载列表失败！");
								}
							}
						}
				},
		        listeners: {
		        beforeload: function(store, operation){
		        	var type = Ext.getCmp("supplyName").getValue();
//					var type = Ext.getCmp("weiXinType").getValue();
//					var	weixinNumber = Ext.getCmp("weixinNumber").getValue();
//        			Ext.apply(store.proxy.extraParams, type);
 //       			this.getProxy().setExtraParam("type",Ext.getCmp("supplyType").getValue());
		        }
		    }
		});
		
		var brandStore = Ext.create("Ext.data.Store",{
			autoLoad:true,
			fields : [
					"brandSid","brandName"
					],
 			proxy: {
 				
						type: "ajax",
						url : _appctx + "/supply/queryBrandListByParam.json",
						getMethod: function(){
							return 'POST';
						},
						reader: {
							type: "json",
							root : "page.list",
//							totalProperty :"total"
						},
						listeners: {
							exception: function(proxy, response, operation, eOpts) {
								if (response.status != 200) {
									Ext.MessageBox.alert("Error", "加载列表失败！");
								}
							}
						}
				},
		        listeners: {
		        beforeload: function(store, operation){
//					var type = Ext.getCmp("weiXinType").getValue();
//					var	weixinNumber = Ext.getCmp("weixinNumber").getValue();
//        			Ext.apply(store.proxy.extraParams, type);
 //       			this.getProxy().setExtraParam("type",Ext.getCmp("supplyType").getValue());
		        }
		    }
		});
		var categroy1Store = Ext.create("Ext.data.Store",{
			autoLoad:true,
			fields : [
					"id","cName"
					],
 			proxy: {
						type: "ajax",
						url : _appctx + "/supply/queryCategoryByParam.json?node=1&channelSid=1",
						getMethod: function(){
							return 'POST';
						},
						reader: {
							type: "json",
							root : "page.list",
//							totalProperty :"total"
						},
						listeners: {
							exception: function(proxy, response, operation, eOpts) {
								if (response.status != 200) {
									Ext.MessageBox.alert("Error", "加载列表失败！");
								}
							}
						}
				},
		        listeners: {
		        beforeload: function(store, operation){
//					var type = Ext.getCmp("weiXinType").getValue();
//					var	weixinNumber = Ext.getCmp("weixinNumber").getValue();
//        			Ext.apply(store.proxy.extraParams, type);
 //       			this.getProxy().setExtraParam("type",Ext.getCmp("supplyType").getValue());
		        }
		    }
		});
		
		this.bBar = Ext.create("Ext.Toolbar",{

			buttonAlign : 'center',
			height : 40,
			items : [{
				
						xtype : 'button',
						preesed : true,
						height : 30,
						width : 80,
						ctCls : 'x-btn-over',
						iconCls : 'button_save',
						text : '保存',
							
						handler:

						function(form,action){
							var form = Ext.getCmp('saveSupplySid').getForm();
							var supply = Ext.getCmp('supplyName').getRawValue();
							var supplySid=Ext.getCmp('supplyName').getValue() ;
							var supplyName=supply.split(supplySid);
							var brand = Ext.getCmp('brandName').getRawValue();
							var brandSid=Ext.getCmp('brandName').getValue() ;
							var brandName=brand.split(brandSid);
							var category = Ext.getCmp('categoryName').getRawValue();
							var categorySid=Ext.getCmp('categoryName').getValue() ;
							var categoryName=category.split(categorySid);
							if (form.isValid()) {
					            form.submit({
					              url:  _appctx + "/supply/insertSupplier.json",
					              method: 'post',
					              submitEmptyText: false,
					           //   waitMsg: '请稍等，系统正在帮您添加',
					              allowBlank : false,
					              params: { 
					                	
					                	supplyName :supplyName,
					                	brandName :brandName,
					                	categoryName: categoryName,
					                	
										
					                },
//					                success:function(form) {
//										Ext.Msg.alert('提示', '添加LOGO成功');
//										Ext.getCmp("SupplyInforView1").getStore().load();
//										thisWindow.close();
//								},
//								failure : function() {
//									Ext.Msg.alert('错误', '操作失败');
//								}
					            })
					            
										Ext.Msg.alert('提示', '添加LOGO成功');
										Ext.getCmp("SupplyInforView1").getStore().load();
										thisWindow.close();
//							
//							
					          }
							
							
						}
					}, {
						xtype : 'button',
						preesed : true,
						height : 30,
						width : 80,
						ctCls : 'x-btn-over',
						iconCls : 'button_cancel',
						text : '取消',
						handler : function() {
							thisWindow.close();
						}
					}]
		})
		
		this.savepanel = Ext.create("Ext.FormPanel",{
			id : 'saveSupplySid',
	//		bodyStyle : 'padding:0 100px  0',
//			height : 550,
			
			frame : true,
			border : false,
			autoScroll : true,
			fileUpload : true,
			layoutConfig : {
				padding : "2"
			},
			items : [{
				layout : 'form',
				items : [ {
					fieldLabel : "供应商",
					id:'supplyName',
					name : "supplySid",
					allowBlank : false,
					listeners:{
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
				     },
				   },
					xtype : "combo",
					store : supplyStore,
					displayField:"supplyName",
					valueField : "sid",
					queryMode: 'remote',
					selectOnFocus: true,
					triggerAction: 'all',
					typeAhead: true,
		//			anchor : '35%',
					
						
				}, {
					fieldLabel : "品牌",
					id:'brandName',
					name : "brandSid",
					allowBlank : false,
					xtype : "combo",
					store : brandStore,
					listeners:{
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
					},
					displayField: "brandName",
					valueField : "brandSid",
					queryMode: 'remote',
					triggerAction: 'all',
					typeAhead: true,
			//		anchor : '35%'
				},  {
					fieldLabel : "一级品类",
					id:'categoryName',
					name : "categorySid",
					allowBlank : false,
					xtype : "combo",
					listeners:{
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
					},
					store : categroy1Store,
					displayField: "cName",
					valueField : "id",
					queryMode: 'remote',
					triggerAction: 'all',
					typeAhead: true,
		//			anchor : '20%',
		//			width : 30
				},
				
				
				{
					
					columnWidth : .5,
					layout : 'form',
					items: [
					        {xtype:'fieldset',title:'图片上传', collapsible:true,
					          items:[
					            {
					              xtype:'filefield',
					              fieldLabel:'上传图片',
					              name:'image1',
					              id:'image1',
					              width :380,
					              buttonText:'上传图片',
					              buttonConfig:{iconCls:'upload'},
					              listeners:{
					                change:function(btn, value, eOpts){
					                  var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;
					                  if ( img_reg.test(value) ) {
					                    var img = Ext.getCmp('img1');
					                    var file = btn.fileInputEl.dom.files[0];
					                    var url = URL.createObjectURL(file);
					                    img.setSrc(url);
					                  } else {
					                    Ext.Msg.alert('提示', '请选择图片类型的文件！');
					                    return ;
					                  }
					                }
					              }
					            },
					          ]
					        },{xtype:'fieldset',title:'图片预览',layout:'column',defaults:{width:130},
					          items:[
					            {xtype:'image',id:'img1'},
					          
					          ]
					        }
					      ],
				}
				]
			} ]
			
		});
		
		// form表单
		this.formpanel = Ext.create("Ext.Panel",{
			id : 'addSupply',
			region : 'center',
	//		height : '100%',
			border : false,
//			height : 60,
	//		width : '100%',
			layout : "column",
			items : [ {
				columnWidth : 1,
				items : [ this.savepanel ]
			} ]
		});
	}
});