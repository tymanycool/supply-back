Ext.define("Supply.SupplyInforView",{
	extend:"Ext.grid.Panel",
	/*requires: [
		'Member.UpdateMessageWindow'
	],*/

	baseUrl : _appctx,
	tbar:null,
	columns:null,
	//supplyStore:null,
	brandStore :null,
	categroy1Store : null,
//	itemsPerPage : 9,
	currentPage : null,
	 supplyName : null,
	 brandName  : null,
	 text : null,
	 sm:null,
	 deliveryStore : null,
	
	constructor: function(config) {
		var me = this;
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			columns: this.columns,
			id : "SupplyInforView1",
			tbar: this.tbar,
			bbar : this.pageBar,
			selModel:this.sm,
			store: this.deliveryStore,
			viewConfig : {
				enableTextSelection : true
			// 启用文字选择
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
            singleSelect : true
            });
		var checkbox = this.sm;
		this.deliveryStore= Ext.create("Ext.data.Store",{
			autoLoad:false,
			pageSize : 20,
			fields : [
			       
					   "sid","supplyName","brandName","categoryName","picUrl","supplySid","brandSid","categorySid"
						],
				proxy: {
						type: "ajax",
						url : _appctx + "/supply/findSupplyList.json",
						getMethod: function(){
							return 'POST';
						},
						reader: {
							type: "json",
							root : "list",
							totalProperty :"total"
						},
						writer : {
							type : 'json',
							encode : true,
							root : 'data',
							expandData : true
						},
						listeners: {
							exception: function(proxy, response, operation, eOpts) {
								if (response.status != 200) {
									Ext.MessageBox.alert("Error", "加载列表失败！111");
								}
							}
						}
				},
		});
		var supplyStore = Ext.create("Ext.data.Store",{
			autoLoad:true,
			//pageSize : this.itemsPerPage,
			
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
							root : "page",
							totalProperty :"total"
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
		//----------------------以下是brandStore--------------------------
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
		//----------------------以下是一级品类-----------------
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
		        }
		    }
		});
		function add(){
		
			var addWin = new Ext.Window({
				title:'添加品牌',
				width:500,  
				height:400,
				layout:'fit',
				
				closable:false,
				
				items : [{
					
					xtype:'form',
					id:'formID',
					items : [ {
						fieldLabel : "供应商",
						id:'supplyName',
						name : "supplySid",
						allowBlank : false,
						width:400,
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
						queryMode: 'remote',
						selectOnFocus: true,
						triggerAction: 'all',
						typeAhead: true
			//			anchor : '35%',
						
							
					}, {
						fieldLabel : "品牌",
						id:'brandName',
						name : "brandSid",
						allowBlank : false,
						xtype : "combo",
						store : brandStore,
						width:400,
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
						typeAhead: true
				//		anchor : '35%'
					},  {
						fieldLabel : "一级品类",
						id:'categoryName',
						name : "categorySid",
						allowBlank : false,
						xtype : "combo",
						width:400,
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
						typeAhead: true
			//			anchor : '20%',
			//			width : 30
					},
					
					
					{
						
						columnWidth : .5,
						layout : 'form',
						items: [
						        {xtype:'fieldset',
						        	title:'图片上传',
						        	collapsible:true,
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
						            {xtype:'image',id:'img1'}
						          
						          ]
						        }
						      ],
					}
					]
				} ],
				buttons:[
						 {
						   text:'保存',
						   handler:function(){
//							   var form = Ext.getCmp('saveSupplySid').getForm();
								var supply = Ext.getCmp('supplyName').getRawValue();
								var supplySid=Ext.getCmp('supplyName').getValue() ;
								var supplyName=supply.split(supplySid);
								var brand = Ext.getCmp('brandName').getRawValue();
								var brandSid=Ext.getCmp('brandName').getValue() ;
								var brandName=brand.split(brandSid);
								var category = Ext.getCmp('categoryName').getRawValue();
								var categorySid=Ext.getCmp('categoryName').getValue() ;
								var categoryName=category.split(categorySid);
								if(Ext.getCmp('formID').getForm().isValid){
								Ext.getCmp('formID').getForm().submit({
							      url : _appctx + '/supply/insertSupplier.json',
							      params: {
							    		supplyName :supplyName,
					                	brandName :brandName,
					                	categoryName: categoryName
								    },
							      method:"POST", 
							      waitMsg : "正在提交数据...",
							      success:function(form,action) {

										Ext.Msg.alert('提示', '添加LOGO成功');
										Ext.getCmp("SupplyInforView1").getStore().load();
										addWin.close();
								},
								failure : function() {
									alert("error");
									Ext.Msg.alert('错误', '操作失败');
								}
								
						          
			                   });
								}
								
//								Ext.Msg.alert('提示', '添加LOGO成功');
//								Ext.getCmp("SupplyInforView1").getStore().reload();
//								 addWin.close();
						  }
						},{
					       text:'取消',
					       handler:function(){
						   addWin.close();
						   }
						}]
				});
			
				addWin.show();

	};
	function liuLan(url){
		//var allsid = checkbox.getSelection();
//		var sid = singleStore.get('sid');
//		var deleteFlag = singleStore.get('deleteFlag');
		var liuLanWin = new Ext.Window({
			title:'浏览图片',
			width:500,  
			height:400,
			layout:'fit',
			
			closable:false,
			
			items : [{
				
				xtype:'form',
				id:'formID',
				items : [   
			        {xtype:'fieldset',title:'图片预览',layout:'column',defaults:{width:500,height:300},
				          items:[
				            {xtype:'image',id:'img1',
				            	autoEl : {
									tag : 'img',
									src : importImagePath+url
								}

				            	},
				          ]
				        }
				  
				]
			} ],
			buttons:[
					 {
					   text:'下载图片',
					   handler:function(grid, rowIndex, colIndex){
							Ext.Msg.show({
								title : '提示消息',
								icon : Ext.MessageBox.QUESTION,
								msg : '确认下载?',
								buttons : Ext.MessageBox.YESNO,
								fn : function(btn) {
									if (btn == 'yes') {
										window.location.href=_appctx+"/supply/downloadSid.json?url="+url;
									}
									liuLanWin.close();
								}
								
							});
						
						}			
					 },{
				       text:'取消',
				       handler:function(){
				    	   liuLanWin.close();
					   }
					}]
			});
		
			liuLanWin.show();

};
			
		var saveStore = this.saveBrandLOGOStore;		
		var supplydataStore = this.deliveryStore;
		this.columns=[	
		              
//						new Ext.grid.RowNumberer({
//							width:50
//						}),
						{
							header : "供应商ID",
							dataIndex : "supplySid",
							hidden:false,
							width: 80,
						},
			         	{
							header : "供应商名称",
							dataIndex : "supplyName",
							width:400,
						},
						{
							header : "品牌商品ID",
							dataIndex : "brandSid",
							hidden:false,
							width: 80,
						},{
							header: "品牌名称",
							dataIndex: "brandName",
							width:150,
						}
						,
						{
							header : "所属分类ID",
							dataIndex : "categorySid",
							hidden:false,
							width: 80,
						},{
							header: "所属分类",
							dataIndex: "categoryName",
							width:150,
						}
						,{text:"操作",
							xtype:'actioncolumn',
							sortable: true,
							width:80,
							items :[{
								text:'下载图片',
								xtype : 'button',
								tooltip: '下载图片',
								icon:_appctx+'/images/download.gif',
								handler:function(grid, rowIndex, colIndex){
									var singleStore = grid.getStore().getAt(rowIndex);
									var url = singleStore.get('picUrl');
									liuLan(url);
								}
							}]
						}
			];
		
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			xtype: toolbar,
			id:"suplyMessageTbar",
			
			items:[{
				xtype: "buttongroup",
				width: '100%',
				maxWidth : '100%',
				height : 50,
//				columns: 15,
				
				items:[
				{	
				xtype : "combo",
				fieldLabel: "供应商名称:",
				id:"supplyType",
				labelWidth : 60,
				//width:50,
				labelAlign : "right",
				editable:true,
				store:supplyStore,
				emptyText : "请选择",
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
				name : "type",
				queryMode: "local",
				displayField:"supplyName",
				valueField: "sid",
				queryMode: 'remote',
				triggerAction: 'all',
				typeAhead: true,
			//	hiddenName : "sid",
				
				width:350,
				//margin:"0 10 0 17",
				},
				   {	
					xtype: "combo",
					fieldLabel: "品牌名称",
					labelWidth : 70,
					id:"brandType",
					emptyText : "请选择",
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
					labelAlign : "right",
					editable:true,
					store: brandStore,
					queryMode: "local",
					displayField: "brandName",
				//	hiddenName : "sid",
					valueField: "brandSid",
					queryMode: 'remote',
					triggerAction: 'all',
					typeAhead: true,
					width:250,
					//margin:"0 10 0 17",
					},
					{//channelSid:1
					xtype: "combo",
					fieldLabel: "一级品类",
					labelWidth : 70,
					emptyText : "请选择",
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
					id:"categroyType",
					labelAlign : "right",
					editable:true,
					store: categroy1Store,
					queryMode: "local",
					displayField: "cName",
				//	hiddenName : "sid",
					valueField: "id",
				    queryMode: 'remote',
					triggerAction: 'all',
					typeAhead: true,
					width: 200,
					//margin:"0 10 0 17",
					},
					
					{xtype:'', width : 15},
					{
						text:'查询',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){
							var supplySid = Ext.getCmp("supplyType").getValue();
							var	brandSid = Ext.getCmp("brandType").getValue();
							var	categorySid = Ext.getCmp("categroyType").getValue();
							
							supplydataStore.load({
			   					params:{
			   						supplySid : supplySid,
			   						brandSid : brandSid,
			   						categorySid : categorySid,
			   					}
			   				});
						}
					},
				
					
				{	 
				text : '添加品牌',
				width:60,
				pressed: true,
				ctCls: 'x-btn-over',
				
						handler: function(grid, rowIndex, colIndex){
//							var singleStore = grid.getStore().getAt(rowIndex);
//							 Ext.create("Supply.AddSupplyBrandView",{
//									id: "AddSupplyBrandView3",
//									title:"供应商管理",
//									url : _appctx,
//								//	itemsPerPage: 20,
//									width: "80%",
//									region: "center",
//									closable:true
//							 }).show();
							add();
						}
				},
				{
					text:'删除品牌',
					width:60,
					pressed: true,
					ctCls: 'x-btn-over',
					handler: function(){
						var sidArr = checkbox.getSelection();
						var sids = "";
						for(var i=0;i<sidArr.length;i++){
							sids = sids + sidArr[i].get('sid') +",";
						}
						if(sids!=null&&sids!=""){
					//	alert(sids);
						Ext.Msg.show({
							title : '提示消息',
							icon : Ext.MessageBox.QUESTION,
							msg : '确认删除?',
							buttons : Ext.MessageBox.YESNO,
							fn : function(btn) {
								if (btn == 'yes') {
									/*for (var i = 0; i < record.length; i++) {
										
									}*/
									Ext.Ajax.request({
										url : _appctx + "/supply/deleteBrandBySid.json",
										_method : 'POST',
										params : {
											_method : "POST",
											sid :sids
										},
										 success:function(form) {
												Ext.Msg.alert('提示', '删除成功');
												Ext.getCmp("SupplyInforView1").getStore().reload();
												
										},
										failure : function() {
											Ext.Msg.alert('信息提示',
													'删除失败，请与管理员联系');
										}
						     });
								}
							}
							
						});
						}else{
							Ext.Msg.alert('信息提示','请选择要删除项');
						}
					}
				},
				]
			}]
		});
		
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.deliveryStore,
			displayInfo : true
		});

	},
listeners:{
		itemdblclick:function(view, record, item){
		//	var record = Ext.getCmp('SupplyInforView1').getSelectionModel().getSelections();
			if(record !=null){
			
				Ext.create("Supply.UpdateSupplyBrandView",{
					id: "updateMessageWindow",
					title:"品牌Logo",
					url : _appctx,
				//	itemsPerPage: 20,
					width: "80%",
					region: "center",
					record : record,
					closable:true
				}).show();
			
			}else{
				Ext.MessageBox.alert("提示","请选择一条记录");
			}
		}
	}
	
	
});