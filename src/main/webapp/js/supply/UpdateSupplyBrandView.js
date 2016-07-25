Ext.define("Supply.UpdateSupplyBrandView",{
	extend: "Ext.window.Window",
	id :"updateMessageWindow",
	requires: [
		'Supply.SupplyInforView'
	],
	url : _appctx,
	tbar:null,
	record : null,
	
	itemsPerPage : 20,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
//			columns: this.columns,
			bbar : this.bBar,
			tbar: this.tbar,
			
			
			
			items : [ this.formpanel ],
			modal : true,
			height : 400,
			width : 500,
			title : "修改品牌",
			
			
			viewConfig : {
				enableTextSelection : true
			// 启用文字选择
			}
		});
	},
	
	initComponents: function() {
//		var thisView = this;
		var baseUrl = this.baseUrl;
		var thisWindow = this;
		var record = this.record;
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
		this.bBar =Ext.create("Ext.Toolbar",{
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
						handler : function(){
							var form = Ext.getCmp('updateSupplySid').getForm();
							
							
							if (form.isValid()) {
								var supply = Ext.getCmp('supplyName').getRawValue();
								var supplySid=Ext.getCmp('supplyName').getValue() ;
								var supplyName=supply.split(supplySid);
								var brand = Ext.getCmp('brandName').getRawValue();
								var brandSid=Ext.getCmp('brandName').getValue() ;
								var brandName=brand.split(brandSid);
								var category = Ext.getCmp('categoryName').getRawValue();
								var categorySid=Ext.getCmp('categoryName').getValue() ;
								var categoryName=category.split(categorySid);
								form.submit({
											waitMsg : "正在提交数据...",
											url : _appctx + "/supply/updateSupplyBillMsg.json",
											method:'post',
											params: { 
							                	
							                	supplyName :supplyName,
							                	brandName :brandName,
							                	categoryName: categoryName,
							                	sid:record.data.sid,
							                },
											success:function(form, action) {
													Ext.Msg.alert('提示', '修改广告成功');
													Ext.getCmp("SupplyInforView1").getStore().load();
													thisWindow.close();
											},
											failure : function() {
												Ext.Msg.alert('错误', '操作失败');
											}
										});
							
							} else {
								alert("必填项不能为空");
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
		
		}),
		this.savepanel = Ext.create("Ext.FormPanel",{
			id : 'updateSupplySid',
		//	bodyStyle : 'padding:0 100px  0',
			height : '100%',
			frame : true,
			border : false,
			autoScroll : true,
			fileUpload : true,
			layoutConfig : {
				padding : "2"
			},
			items : [{
				layout : 'form',
				items : [  {
					
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
			               }
						},
						xtype : "combo",
						store : supplyStore,
						displayField:"supplyName",
						valueField : "sid",
						typeAhead: true,
						value: record.data.supplySid
				}, {
					fieldLabel : "品牌",
					id:'brandName',
					name : "brandSid",
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
		               }
					},
					xtype : "combo",
					store : brandStore,
					displayField: "brandName",
					valueField : "brandSid",
					typeAhead: true,
					value: record.data.brandSid
				},  {
					fieldLabel : "一级品类",
					id:'categoryName',
					name : "categorySid",
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
		               }
					},
					xtype : "combo",
					store : categroy1Store,
					displayField: "cName",
					valueField : "id",
					typeAhead: true,
					value: record.data.categorySid
				},{
					
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
					        },{xtype:'fieldset',title:'图片预览',layout:'column',defaults:{width:200,height :135},
					          items:[
					            {xtype:'image',id:'img1',
					            	autoEl : {
										tag : 'img',
										src : importImagePath+record.data.picUrl
									}

					            	},
					          ]
					        }
					      ],
				} ]
			} ],
			
			
			

		});
		
		// form表单
		this.formpanel = Ext.create("Ext.Panel",{
			id : 'addSupply',
			region : 'center',
			height : '100%',
//			height : 60,
//			width : '70%',
			layout : "column",
			items : [{
				columnWidth : 1,
				items : [ this.savepanel ]
			}]
		})
	}
});