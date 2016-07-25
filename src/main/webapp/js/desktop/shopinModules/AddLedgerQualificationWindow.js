/**
 * 资质台账添加
 * author liuchunlong
 */

//定义供应商基本资料界面
Ext.define("ShopinDesktop.SupplyInfoLQAddView", {
	extend: "Ext.Panel",
	
	id: "supplyInfoLQAddView",
	panel: null,
	baseUrl: "",
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		var baseUrl = this.baseUrl;
		
		var taxRegistrationStore= new Ext.data.ArrayStore ({
			fields: ['taxRegistrationCode','value'],
			data : [
				["有","true"],
				["无","false"]
			]
		});
		
		var taxpayerStore= new Ext.data.ArrayStore ({
			fields: ['taxpayerCode','value'],
			data : [
				["税票","税票"],
				["有","有"],
				["无","无"]
			]
		});
		
		var isblacklistStore= new Ext.data.ArrayStore ({
			fields: ['isblacklistCode','value'],
			data : [
				["否","false"],
				["是","true"]
			]
		});
		
		this.superclass.constructor.call(this, {
			
			layout : 'anchor',
			autoscroll: true,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			bodyPadding: "15 15 15 15",
			defaults : {
				anchor : '90%'
			},
			items:[{
				xtype: 'fieldset',
				title: "<span style='font-weight:bold;'>供应商基本资料</span>",
				border: 1,
				defaultType: 'textfield',
				layout: 'anchor',
	            defaults: {
	                anchor: '100%'
	            },
	            items:[
	            	{
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
				        		xtype:"label",
				        		//hidden:"true",
				        		style:{
				        			color:"red",
				        			fontWeight:"bold"
				        		},
				        		name:"msg",
				        		id:"msg"
				        	}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
		            {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
								xtype:"textfield",
								fieldLabel:"供应商编码",
								name:"supplierCode",
								allowBlank:false,
								width : 300,
								
								listeners: {
					             'blur': function(f){
											Ext.Ajax.request({
												method :'POST',
												url :baseUrl + "/ledgerQualification/selectBySupplierCodeOfSupplyInfo.json",
												success:function(response) {
													var result = Ext.decode(response.responseText);
													if(result.obj != null) {
														//Ext.getCmp("msg").show();
														Ext.getCmp("msg").setText("该供应商已存在，如果你更改下面的信息，将不可恢复。");
														Ext.getCmp("supplierName").setValue(result.obj.supplierName);
														Ext.getCmp("businessLicense").setValue(result.obj.businessLicense);
														Ext.getCmp("nationalTaxRegistrationId").select(taxRegistrationStore.getAt(taxRegistrationStore.find('value',result.obj.nationalTaxRegistration)));
														Ext.getCmp("landTaxRegistrationId").select(taxRegistrationStore.getAt(taxRegistrationStore.find('value',result.obj.landTaxRegistration)));
														Ext.getCmp("generalTaxpayerId").select(taxpayerStore.getAt(taxpayerStore.find('value',result.obj.generalTaxpayer)));
														Ext.getCmp("isblacklistId").select(isblacklistStore.getAt(isblacklistStore.find('value',result.obj.isblacklist)));
													}else {
														//Ext.getCmp("msg").hide();
														Ext.getCmp("msg").setText("该供应商不存在，你可以输入信息，新增该供应商。");
														Ext.getCmp("supplierName").setValue(null);
														Ext.getCmp("businessLicense").setValue(null);
														Ext.getCmp("nationalTaxRegistrationId").select(taxRegistrationStore.getAt(-1));
														Ext.getCmp("landTaxRegistrationId").select(taxRegistrationStore.getAt(-1));
														Ext.getCmp("generalTaxpayerId").select(taxpayerStore.getAt(-1));
														Ext.getCmp("isblacklistId").select(isblacklistStore.getAt(-1));
													}
												},
												failure:function() {
													Ext.Msg.alert("获取供应商信息失败！");
												},
												params:"supplierCode=" + Ext.getCmp("ledgerQualificationInforPanel").getForm().findField("supplierCode").getValue()
											});
					                  }  
							     }
							},{
								xtype:"textfield",
								fieldLabel:"供应商名称",
								name:"supplierName",
								id:"supplierName",
								width : 300,
								labelWidth:100,
								labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
								xtype:"textfield",
								fieldLabel:"营业执照",
								name:"businessLicense",
								id:"businessLicense",
								width : 300
							},
							
							{
								xtype:"combo",								//组件类型：ComboBox,下拉列表框
								emptyText:"请选择",
								fieldLabel : '国税登记证',						//组件标签
								editable:false,								//组件是否可编辑，否
								id:"nationalTaxRegistrationId",				//组件id
								store: taxRegistrationStore,				//组件数据
								queryMode: "local",							//本地
								valueField: 'value',						//组件的值
								displayField:'taxRegistrationCode',			//组件显示的列
								//hiddenName:'nationalTaxRegistration',		//隐藏表单项<input name='nationalTaxRegistration' type='hidden'/>
								triggerAction : 'all',						//显示所有列表项
								name:'nationalTaxRegistration',				//组件name
								width : 300,
								labelWidth:100,
								labelAlign:"right",
								mode:'local'		 						//本地
								
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	            	{
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
								xtype:"combo",							//组件类型：ComboBox,下拉列表框
								emptyText:"请选择",
								fieldLabel : '地税登记证',					//组件标签
								editable:false,							//组件是否可编辑，否
								id:"landTaxRegistrationId",				//组件id
								store: taxRegistrationStore,			//组件数据
								queryMode: "local",						//本地
								valueField: 'value',					//组件的值
								displayField:'taxRegistrationCode',		//组件显示的列
								//hiddenName:'landTaxRegistration',		//隐藏表单项<input name='landTaxRegistration' type='hidden'/>
								triggerAction : 'all',					//显示所有列表项
								name:'landTaxRegistration',				//组件name
								width : 300,
								mode:'local'		 					//本地
								
							},
							
							{
								xtype:"combo",							//组件类型：ComboBox,下拉列表框
								emptyText:"请选择",
								fieldLabel : '一般纳税人',					//组件标签
								editable:false,							//组件是否可编辑，否
								id:"generalTaxpayerId",					//组件id
								store: taxpayerStore,					//组件数据
								queryMode: "local",						//本地
								valueField: 'value',					//组件的值
								displayField:'taxpayerCode',			//组件显示的列
								//hiddenName:'generalTaxpayer',			//隐藏表单项<input name='generalTaxpayer' type='hidden'/>
								triggerAction : 'all',					//显示所有列表项
								name:'generalTaxpayer',					//组件name
								width : 300,
								labelWidth:100,
								labelAlign:"right",
								mode:'local'		 					//本地
								
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
								xtype:"combo",						//组件类型：ComboBox,下拉列表框
								emptyText:"请选择",
								fieldLabel : '黑名单',					//组件标签
								editable:false,						//组件是否可编辑，否
								id:"isblacklistId",					//组件id
								store: isblacklistStore,			//组件数据
								queryMode: "local",					//本地
								valueField: 'value',				//组件的值
								displayField:'isblacklistCode',		//组件显示的列
								//hiddenName:'isblacklist',			//隐藏表单项<input name='isblacklist' type='hidden'/>
								triggerAction : 'all',				//显示所有列表项
								name:'isblacklist',					//组件name
								width : 300,
								mode:'local'		 				//本地
								
							}
				        ]
		            }
	                
	            ]
			}],
			
			title: "供应商基本资料"
		});
	}
	
});
//定义商标注册信息界面
Ext.define("ShopinDesktop.TrademarkRegistrationInformationLQAddView", {
	extend: "Ext.Panel",
	
	id: "trademarkRegistrationInformationLQAddView",
	panel: null,
	baseUrl: "",
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		
		
		var certificateTypeStore= new Ext.data.ArrayStore ({
			fields: ['certificateTypeCode','value'],
			data : [
				["身份证","身份证"],
				["护照","护照"]
			]
		});
		
		var actingLevelStore= new Ext.data.ArrayStore ({
			fields: ['actingLevelCode','value'],
			data : [
				["一级","一级"],
				["二级","二级"],
				["三级","三级"],
				["四级","四级"],
				["五级","五级"],
				["六级","六级"],
				["自己注册","自己注册"]
			]
		});
		
		
		
		this.superclass.constructor.call(this, {
			
			layout : 'anchor',
			autoscroll: true,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			bodyPadding: "15 15 15 15",
			defaults : {
				anchor : '90%'
			},
			items:[{
				xtype: 'fieldset',
				title: "<span style='font-weight:bold;'>商标注册信息</span>",
				border: 1,
				defaultType: 'textfield',
				layout: 'anchor',
	            defaults: {
	                anchor: '100%'
	            },
	            items:[
		            {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"注册证号码",
							name:"registrationNumber",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"核定类别",
							name:"approvedCategory",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"结束日期",
							name:"endDate",
							width : 300
							},
							{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"申请日期",
							name:"applicationDate",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"个人商标持有人姓名",
							name:"trademarkHolderName",
							width : 300
							},
							
							{
							xtype:"combo",						//组件类型：ComboBox,下拉列表框
							emptyText:"请选择",
							fieldLabel : '证件类型',				//组件标签
							editable:false,						//组件是否可编辑，否
							id:"certificateTypeId",				//组件id
							store: certificateTypeStore,		//组件数据
							queryMode: "local",					//本地
							valueField: 'value',				//组件的值
							displayField:'certificateTypeCode',	//组件显示的列
							//hiddenName:'certificateType',		//隐藏表单项<input name='certificateType' type='hidden'/>
							triggerAction : 'all',				//显示所有列表项
							name:'certificateType',				//组件name
							width : 300,
							labelWidth:100,
							labelAlign:"right",
							mode:'local'		 				//本地
							
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"证件号码",
							name:"certificateNumber",
							width : 300
							},
							{
							xtype:"combo",					//组件类型：ComboBox,下拉列表框
							emptyText:"请选择",
							fieldLabel : '代理级别',			//组件标签
							editable:false,					//组件是否可编辑，否
							id:"actingLevelId",				//组件id
							store: actingLevelStore,		//组件数据
							queryMode: "local",				//本地
							valueField: 'value',			//组件的值
							displayField:'actingLevelCode',	//组件显示的列
							//hiddenName:'actingLevel',		//隐藏表单项<input name='actingLevel' type='hidden'/>
							triggerAction : 'all',			//显示所有列表项
							name:'actingLevel',				//组件name
							width : 300,
							labelWidth:100,
							labelAlign:"right",
							mode:'local'		 			//本地
							
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textarea",
							width:600,
							height:150,
							fieldLabel:"单店授权",
							name:"singleStoreAuthorization"
							}
				        	
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
							{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"一级代理-日期",
							name:"firstProxyDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"一级代理-批注",
							name:"firstProxyAnnotation",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"二级代理-日期",
							name:"secondProxyDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"二级代理-批注",
							name:"secondProxyAnnotation",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"三级代理-日期",
							name:"thirdProxyDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"三级代理-批注",
							name:"thirdProxyAnnotation",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"四级代理-日期",
							name:"forthProxyDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"四级代理-批注",
							name:"forthProxyAnnotation",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"五级代理-日期",
							name:"fifthProxyDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"五级代理-批注",
							name:"fifthProxyAnnotation",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}			
				        ]
		            },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"六级代理-日期",
							name:"sixthProxyDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"六级代理-批注",
							name:"sixthProxyAnnotation",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            }
	                
	            ]
			}],

			title: "商标注册信息"
		});
	}
	
});

//定义质检报告界面
Ext.define("ShopinDesktop.InspectionReportLQAddView", {
	extend: "Ext.Panel",
	
	id: "inspectionReportLQAddView",
	panel: null,
	baseUrl: "",
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		var gridCheckBox = Ext.create('Ext.selection.CheckboxModel',{
					checkOnly:true//屏蔽原有的表格选择模式，只能通过复选框进行选择
		});
		
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			clicksToEdit : 2,
			//配置点击的次数来移动到一个新的行，如果它是可见的，激活编辑下一行的行编辑器。 这个值默认与 clicksToEdit 的属性值相同.
	        clicksToMoveEditor: 2,
			autoCancel: true
		});
		
		//修改可编辑表格显示文字为中文，否则为英文
		if (Ext.grid.RowEditor) {
			Ext.apply(Ext.grid.RowEditor.prototype, {
				saveBtnText: '保存',
				cancelBtnText: '取消',
				errorsText: '错误信息',
				dirtyText: '已修改,你需要提交或取消变更'
			});
		}
		
		var inspectionStore = Ext.create('Ext.data.JsonStore', {
				autoLoad : false,
				fields : [  {
					name : 'inspectionType',
					type : 'string'
				}, {
					name : 'inspectionDate',
					type : 'date',
					dateFormat: 'Y-m-d'
				}, {
					name : 'inspectionContent',
					type : 'string'
				}],
				storeId : 'inspectionStore',
				listeners : {
					beforeload : function(store, operation, eOpts) {
			
			
						
						
						
					}
				}
		});
		
		Ext.define('inspectionItem', {
	        fields : [  {
					name : 'inspectionType',
					type : 'string'
				}, {
					name : 'inspectionDate',
					type : 'date',
					dateFormat: 'Y-m-d'
				}, {
					name : 'inspectionContent',
					type : 'string'
				}]
   		 });
		var inspectionTypeStore= new Ext.data.ArrayStore ({
			fields: ['inspectionTypeCode','value'],
			data : [
				["有质检（有期限）","有质检（有期限）"],
				["有质检（无期限）","有质检（无期限）"],
				["无质检","无质检"]
			]
		});
		

		
		var inspectionPanel = Ext.create("Ext.grid.Panel",{
				id : 'inspectionPanel',
				columns : [
					{
						dataIndex : 'inspectionType',
						text :'质检类型',
						width:110,
						editor: {
							xtype:"combo",							//组件类型：ComboBox
							emptyText:"请选择",
							editable:false,							//组件是否可编辑，否
							id:"inspectionTypeId",					//组件id
							store: inspectionTypeStore,				//组件数据
							queryMode: "local",						//本地
							valueField: 'value',					//组件的值
							displayField:'inspectionTypeCode',		//组件显示的列
							//hiddenName:'inspectionType',			//隐藏表单项<input name='inspectionType' type='hidden'/>
							triggerAction : 'all',					//显示所有列表项
							name:'inspectionType',					//组件name
							mode:'local',		 					//本地
							listeners:{
								select:function(combo,record,opts) {
									var value = Ext.getCmp("inspectionTypeId").getValue();
									
									
									
									if(value == "有质检（有期限）") {
										Ext.getCmp('inspectionDateId').enable();
									}else if(value == "有质检（无期限）" || value == "无质检") {
										//清空日期选择框中的日期
										Ext.getCmp('inspectionDateId').setValue(null);
										//清空文本框中原有日期数据
										//.......
										Ext.getCmp('inspectionDateId').disable();
									}
								}
							}
						}
					},{
						dataIndex : 'inspectionDate',
						text :'质检有效期',
						xtype: 'datecolumn',
						format:'Y-m-d',
						width:120,
						editor: {
			                 xtype:'datefield',
			                 id:'inspectionDateId',
			                 format:'Y-m-d',
			                 editable:false
				        }
					},{
						dataIndex : 'inspectionContent',
						text : '质检内容',
						width:250,
						editor: {
			                 xtype:'textfield'
				        }
					},{
					text:"操作",
					xtype:'actioncolumn',
					sortable: true,
					items:[{
						text:'删除',
						xtype : 'button',
						tooltip: '删除',
						icon:_appctx+'/images/delete.gif',
						handler:function(grid, rowIndex, colIndex){
						
							rowEditing.cancelEdit();//取消修改状态
						
							//取消选中所有的行
							var sm = grid.getSelectionModel();//获取选择模型
							sm.deselect(sm.getSelection());
							//选中当前行
							sm.select(rowIndex);
							//删除选中行
							inspectionStore.remove(sm.getSelection());
						}
					}]
				}],
				 tbar: [
						 {
				            text: '添加',
				            xtype : 'button',
				            handler : function() {
							 	rowEditing.cancelEdit();//取消修改状态
								inspectionStore.insert(0, Ext.create("inspectionItem",{}));
								rowEditing.startEdit(0, 0);//默认编辑状态是第一行第一列
								
								//分析（ analyse）：当用户新增质检报告行时，创建日期选择框，并初始化为可用状态。
								//设置日期选择框可用
							 	Ext.getCmp('inspectionDateId').enable();	
								
							 	
							 	//取消当前新增行的选中的状态
								var sm = inspectionPanel.getSelectionModel();//获取选择模型
								sm.deselect(sm.getSelection());
				            }
				        },
				        //批量删除
				        {
				            text: '删除',
				            xtype : 'button',
				            handler : function() {
							 
							 	rowEditing.cancelEdit();//取消修改状态
								
								var sm = inspectionPanel.getSelectionModel();//获取选择模型
								var selected = sm.selected;
								if(selected.getCount() == 0) {
									Ext.Msg.alert("提示", "请选择至少一条记录！");
								}else{
									Ext.MessageBox.confirm('确认', '你确定要进行此操作吗？',function (e){
										if(e=='yes') {
	
							               inspectionStore.remove(sm.getSelection());//删除选中行
	
										}
						             });
									
								}
				            }
				        }
					 ],
		        plugins : [ rowEditing ],
				store :inspectionStore,
				height:200,
				columnLines : true,
				autoscroll: true,
				selModel: gridCheckBox,
				
				listeners:{
					itemdblclick:function(view, record, item){
						//分析（ analyse）：这里监听双击事件，即修改事件，需要进行初始化操作，根据下拉列表框的值设置日期选择框是否可用。如果是新增则直接设置日期选择框为可用状态。
						//获取质检类型
						var inspectionTypeValue = record.data.inspectionType;
						if(inspectionTypeValue == "有质检（有期限）") {
							Ext.getCmp('inspectionDateId').enable();
						} else if(inspectionTypeValue == "有质检（无期限）" || inspectionTypeValue == "无质检") {
							Ext.getCmp('inspectionDateId').disable();
						}
					}
				}
				
			});
		
		
		
		
		
		
		this.superclass.constructor.call(this, {
			layout : 'anchor',
			autoscroll: true,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			bodyPadding: "15 15 15 15",
			defaults : {
				anchor : '90%'
			},
			items:[{
				xtype: 'fieldset',
				title: "<span style='font-weight:bold;'>质检报告</span>",
				border: 1,
				defaultType: 'textfield',
				layout: 'anchor',
	            defaults: {
	                anchor: '100%'
	            },
	            items:[
		            {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
								xtype:"textfield",
								fieldLabel:"所有质检信息",
								hidden:true,
								name:"inspectionAllInfo",
								id:"inspectionAllInfo",
								width : 300
							},
				        	inspectionPanel
				        ]
		            }
	            ]
			}],
			
			title: "质检报告",
			
			
		});
		
	}
	
});


//定义基本信息界面
Ext.define("ShopinDesktop.BasicInformationLQAddView", {
	extend: "Ext.Panel",
	
	id: "basicInformationLQAddView",
	panel: null,
	baseUrl: "",
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		var statusStore= new Ext.data.ArrayStore ({
			fields: ['statusCode','value'],
			data : [
				["在柜","在柜"],
				["撤柜","撤柜"],
				["未合作","未合作"],
				["计划上柜","计划上柜"]
			]
		});
		
		var brandLevelStore= new Ext.data.ArrayStore ({
			fields: ['brandLevelCode','value'],
			data : [
				["A","A"],
				["B","B"],
				["C","C"],
				["待选池","待选池"],
				["池无","池无"]
			]
		});
		
		
		var validStore= new Ext.data.ArrayStore ({
			fields: ['validCode','value'],
			data : [
				["有效","true"],
				["无效","false"]
			]
		});
		
		
		var categoryStore= new Ext.data.ArrayStore ({
			fields: ['categoryCode','value'],
			data : [
				["女装","女装"],
				["男装","男装"],
				["运动","运动"],
				["户外","户外"],
				["皮具","皮具"],
				["儿童","儿童"],
				["内衣","内衣"],
				["休闲","休闲"],
				["服饰","服饰"],
				["羽绒服","羽绒服"],
				["毛纺","毛纺"],
				["鞋","鞋"],
				["租赁","租赁"]
			]
		});
		
		
		var approvalStatusStore= new Ext.data.ArrayStore ({
			fields: ['approvalStatusCode','value'],
			data : [
				["审核通过","审核通过"],
				["审核未通过","审核未通过"],
				["未审核","未审核"]
			]
		});
		
		
		this.superclass.constructor.call(this, {
			
			
			layout : 'anchor',
			autoscroll: true,
			bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
			bodyPadding: "15 15 15 15",
			defaults : {
				anchor : '90%'
			},
			items:[{
				xtype: 'fieldset',
				title: "<span style='font-weight:bold;'>基本信息</span>",
				border: 1,
				defaultType: 'textfield',
				layout: 'anchor',
	            defaults: {
	                anchor: '100%'
	            },
	            items:[
		            {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"工作联系单截止日期",
							name:"expirationDate",
							width : 300
							},
							
							{
							xtype:"combo",				//组件类型：ComboBox,下拉列表框
							emptyText:"请选择",
							fieldLabel : '状态',			//组件标签
							editable:false,				//组件是否可编辑，否
							id:"statusId",				//组件id
							store: statusStore,			//组件数据
							queryMode: "local",			//本地
							valueField: 'value',		//组件的值
							displayField:'statusCode',	//组件显示的列
							//hiddenName:'status',		//隐藏表单项<input name='status' type='hidden'/>
							triggerAction : 'all',		//显示所有列表项
							name:'status',				//组件name
							width : 300,
							labelWidth:100,
							labelAlign:"right",
							mode:'local'		 		//本地
							
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"移动电话(一)",
							name:"mobilePhone1",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"移动电话(一)姓名",
							name:"mobilePhone1Name",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"移动电话(二)",
							name:"mobilePhone2",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"移动电话(二)姓名",
							name:"mobilePhone2Name",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"固定电话",
							name:"fixedTelephone",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"固定电话姓名",
							name:"fixedTelephoneName",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"combo",					//组件类型：ComboBox,下拉列表框
							emptyText:"请选择",
							fieldLabel : '品牌级别',			//组件标签
							editable:false,					//组件是否可编辑，否
							id:"brandLevelId",				//组件id
							store: brandLevelStore,			//组件数据
							queryMode: "local",				//本地
							valueField: 'value',			//组件的值
							displayField:'brandLevelCode',	//组件显示的列
							//hiddenName:'brandLevel',		//隐藏表单项<input name='brandLevel' type='hidden'/>
							triggerAction : 'all',			//显示所有列表项
							name:'brandLevel',				//组件name
							width : 300,
							mode:'local'		 			//本地
							
							},
							{
							xtype:"combo",					//组件类型：ComboBox,下拉列表框
							emptyText:"请选择",
							fieldLabel : '品类',				//组件标签
							editable:false,					//组件是否可编辑，否
							id:"categoryId",				//组件id
							store: categoryStore,			//组件数据
							queryMode: "local",				//本地
							valueField: 'value',			//组件的值
							displayField:'categoryCode',	//组件显示的列
							//hiddenName:'category',		//隐藏表单项<input name='category' type='hidden'/>
							triggerAction : 'all',			//显示所有列表项
							name:'category',				//组件name
							width : 300,
							labelWidth:100,
							labelAlign:"right",
							mode:'local'		 			//本地
							
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"品牌名称",
							name:"brandName",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"报关单",
							name:"declaration",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textarea",
							width:600,
							height:150,
							fieldLabel:"问题汇总描述",
							name:"descriptionProblem"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"信息维护日期",
							name:"informationMaintenanceDate",
							width : 300
							},
							{
							xtype:"textfield",
							fieldLabel:"信息维护人姓名",
							name:"informationMaintenanceName",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"combo",						//组件类型：ComboBox,下拉列表框
							emptyText:"请选择",
							fieldLabel : '审核状态',				//组件标签
							hidden:true,
							editable:false,						//组件是否可编辑，否
							id:"approvalStatusId",				//组件id
							store: approvalStatusStore,			//组件数据
							queryMode: "local",					//本地
							valueField: 'value',				//组件的值
							displayField:'approvalStatusCode',	//组件显示的列
							//hiddenName:'approvalStatus',		//隐藏表单项<input name='approvalStatus' type='hidden'/>
							triggerAction : 'all',				//显示所有列表项
							name:'approvalStatus',				//组件name
							width : 300,
							mode:'local'		 				//本地
							
							},
							
							{
							xtype:"datefield",
							format: 'Y-m-d',
							fieldLabel:"复审日期",
							hidden:true,
							name:"reviewDate",
							width : 300,
							labelWidth:100,
							labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textfield",
							fieldLabel:"复审人姓名",
							hidden:true,
							name:"reviewName",
							width : 300
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"textarea",
							width:600,
							height:150,
							fieldLabel:"备注",
							name:"mark"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
		            	xtype: 'container',
				        layout: 'hbox',
				        items:[
				        	{
							xtype:"combo",				//组件类型：ComboBox
							emptyText:"请选择",
							fieldLabel : '是否有效',		//组件标签
							editable:false,				//组件是否可编辑，否
							id:"validId",				//组件id
							store: validStore,			//组件数据
							queryMode: "local",			//本地
							valueField: 'value',		//组件的值
							displayField:'validCode',	//组件显示的列
							//hiddenName:'valid',		//隐藏表单项<input name='valid' type='hidden'/>
							triggerAction : 'all',		//显示所有列表项
							name:'valid',				//组件name
							width : 300,
							mode:'local'		 		//本地
							
							}
				        ]
		            }
	            ]
			}],
			title: "基本信息"
		});
		
		Ext.getCmp("approvalStatusId").select(approvalStatusStore.getAt(approvalStatusStore.find('value',"未审核")));
		Ext.getCmp("validId").select(validStore.getAt(validStore.find('value',"true")));
	}
	
});


Ext.define("ShopinDesktop.AddLedgerQualificationWindow", {
	extend: "Ext.window.Window",
	
	id: "addLedgerQualificationWindow",
	panel: null,
	icon: null,
	baseUrl: "",
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			width:940,
			height:480,
			modal: true,
			constrain: true,
			layout: "fit",
			items: [this.panel],
			title: "资质信息添加"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var baseUrl = this.baseUrl;
		var treeStore = Ext.create("Ext.data.TreeStore",{
				root:{
					cls : 'feeds-node',
					text : '资质台账',
					expanded : true,
					children:[{
						text: "供应商基本资料",
						iconCls:'button_tree',
						leaf: true,
						id:"supplyInfoLQAdd",
					},{
						text: "商标注册信息",
						iconCls:'button_tree',
						leaf: true,
						id:"trademarkRegistrationInformationLQAdd"
					},{
						text: "质检报告",
						iconCls:'button_tree',
						leaf: true,
						id:"inspectionReportLQAdd"
					},{
						text: "基本信息",
						iconCls:'button_tree',
						leaf: true,
						id:"basicInformationLQAdd"
					}
					]
				},
			});
		
		
		//初始化子界面
		//初始化供应商基本资料界面
		var supplyInfoLQAddView = Ext.create("ShopinDesktop.SupplyInfoLQAddView", {
											id: "supplyInfoLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										});
		
		//初始化商标注册信息界面
		var trademarkRegistrationInformationLQAddView = Ext.create("ShopinDesktop.TrademarkRegistrationInformationLQAddView", {
											id: "trademarkRegistrationInformationLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										});
		
		//初始化质检报告界面
		var inspectionReportLQAddView = Ext.create("ShopinDesktop.InspectionReportLQAddView", {
											id: "inspectionReportLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										});
		//初始化基本信息界面
		var basicInformationLQAddView = Ext.create("ShopinDesktop.BasicInformationLQAddView", {
											id: "basicInformationLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										});
		
		this.panel = Ext.create("Ext.form.Panel", {
			
			id:'ledgerQualificationInforPanel',
			buttonAlign: "center",
			buttons: [{
				text: "保存",
				handler: function(button, e) {
					thisWindow.saveEdit();
				}
			},{
				text: "取消",
				handler: function(button, e) {
					thisWindow.cancel();
				}
			}],
			defaultType: "textfield",
			layout:"border",
			items:[Ext.create("Ext.tree.TreePanel",{
					title : '导航管理',
			        region:'west',
			        split:true,
			        width: "20%",
			        collapsible:true,
			        store : treeStore,
			        rootVisible : false,
					listeners:{
						  itemclick:function(view, record, item){
							  var newId = record.data.id;									//导航菜单项id
							  var tabId = newId+'View';										//导航菜单项点击所打开的窗体的id
							  var tab = Ext.getCmp(tabId);									//导航菜单项点击所打开的窗体
							  
							  
							  if (tab == undefined) {										//如果没有打开窗体
 									if(newId=="supplyInfoLQAdd"){								//点击的是供应商基本资料
									  Ext.getCmp('addLedgerQualificationView').add(
										Ext.create("ShopinDesktop.SupplyInfoLQAddView", {
											id: "supplyInfoLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										})
									  );
									  Ext.getCmp('addLedgerQualificationView').setActiveTab(Ext.getCmp("supplyInfoLQAddView"));
								  }else if(newId=="trademarkRegistrationInformationLQAdd"){	//点击的是商标注册信息
									  Ext.getCmp('addLedgerQualificationView').add(
										Ext.create("ShopinDesktop.TrademarkRegistrationInformationLQAddView", {
											id: "trademarkRegistrationInformationLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										})
									  );
									  Ext.getCmp('addLedgerQualificationView').setActiveTab(Ext.getCmp("trademarkRegistrationInformationLQAddView"));
								  }else if(newId=="inspectionReportLQAdd"){					//点击的是质检报告
									  Ext.getCmp('addLedgerQualificationView').add(
										Ext.create("ShopinDesktop.InspectionReportLQAddView", {
											id: "inspectionReportLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										})
									  );
									  Ext.getCmp('addLedgerQualificationView').setActiveTab(Ext.getCmp("inspectionReportLQAddView"));
								  }else if(newId=="basicInformationLQAdd"){					//点击的是基本信息
									  Ext.getCmp('addLedgerQualificationView').add(
										Ext.create("ShopinDesktop.BasicInformationLQAddView", {
											id: "basicInformationLQAddView",
											baseUrl: baseUrl,
											caller: thisWindow,
											closable:true
										})
									  );
									  Ext.getCmp('addLedgerQualificationView').setActiveTab(Ext.getCmp("basicInformationLQAddView"));
								  }
							 }else{
								 //如果该窗体已被创建，则判断该窗体是否已存在于TabPanel中。
								 if(isExitTab(tabId)) {
									 //如果该窗体已存在于TabPanel中，则激活。
								 	Ext.getCmp('addLedgerQualificationView').setActiveTab(tab);
								 }else {
									 //如果该窗体不存在于TabPanel中，则将该窗体添加到TabPanel中，并激活。
									Ext.getCmp('addLedgerQualificationView').add(tab);
									Ext.getCmp('addLedgerQualificationView').setActiveTab(tab);
								 }
							 }
							 //判断该id的tab是否存在于TabPanel中
							 function isExitTab(tabId) {
								 //获取tab容器组件 
								 var tabsPanel = Ext.getCmp('addLedgerQualificationView');
								 var tabItems = tabsPanel.items.items;
								 for(var i = 0; i < tabItems.length; i++) {
									 if(tabId == tabItems[i].id){
										 return true;
									 }
								 }
								 return false;
							 };
						  }
					}
			       }),Ext.create("Ext.TabPanel",{
						id:"addLedgerQualificationView",
						width: "80%",
						region:'center',
						activeTab: 0,
						enableTabScroll : true,			
			 			frame:true, 
			 			items:[
			 				supplyInfoLQAddView,
			 				trademarkRegistrationInformationLQAddView,
			 				inspectionReportLQAddView,
			 				basicInformationLQAddView
			 			]
					})
			       ]
		});
		
		
			
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var baseUrl = this.baseUrl;
		var editForm = this.panel.getForm();
		
		if (editForm.isValid()) {
			
			var inspectionStore=Ext.getCmp('inspectionPanel').getStore();//获取质检信息store
			var inspectionArr= new Array();//创建一个存储质检信息的数组
			var jsonInspection = "";//创建一个存储质检信息的json字符串
			var start = "[";
			var end = "]";
			inspectionStore.each(function(record){  
            	jsonInspection = "{inspectionType:'"+record.get('inspectionType')+"',inspectionDate:'"+record.get('inspectionDate')+"',inspectionContent:'"+record.get('inspectionContent')+"'}";
                inspectionArr.push(jsonInspection);
            });
            editForm.findField('inspectionAllInfo').setValue(start+inspectionArr+end);
                
           	//alert(start+inspectionArr+end);
			
			
			editForm.submit({
				url : baseUrl + "/ledgerQualification/insertLedgerQualificationCustom.json",
				success: function(form, action) {
					 if(action.result.success == "true"){
						 	Ext.getCmp('ledgerQualificationView').getStore().load();
							thisWindow.close();
							caller.pageBar.doRefresh();
						}else if(action.result.success == "false"){
							if(action.result.errorCode == "10000"){
								Ext.Msg.alert('错误',action.result.memo);
							}else{
								Ext.Msg.alert('错误',"保存失败！");
							}
					}
				},
				failure: function(form, action) {
					switch (action.failureType) {
						case Ext.form.action.Action.CONNECT_FAILURE:
							Ext.MessageBox.alert("Error", "保存失败。请检查网络连接或确认服务器是否运行");
							break;
						case Ext.form.action.Action.SERVER_INVALID:
							var response = Ext.JSON.decode(action.response.responseText);
							Ext.MessageBox.alert("Error", "保存失败。" + response.errorMsg);
							break;
						case Ext.form.action.Action.LOAD_FAILURE:
							Ext.MessageBox.alert("Error", "保存失败。远程服务器返回了不能识别的数据格式，请联系管理员");
					}
				}
			});
		}
	},
	cancel: function() {
		this.close();
	}
});