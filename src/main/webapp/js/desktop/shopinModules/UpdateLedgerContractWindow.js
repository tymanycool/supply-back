/**
 * 合同台账更新
 * author liuchunlong
 */

//定义供应商基本资料界面
Ext.define("ShopinDesktop.SupplyInfoLCUpdateView", {
	extend: "Ext.Panel",
	
	id: "supplyInfoLCUpdateView",
	panel: null,
	baseUrl: "",
	record: null,
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		var baseUrl = this.baseUrl;
		
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
								value:this.record.data.supplierCode,
								id:"supplierCode",
								width : 300,
								listeners: {
						             'blur': function(f){
												Ext.Ajax.request({
													method :'POST',
													url :baseUrl + "/ledgerContract/selectBySupplierCodeOfSupplyInfo.json",
													success:function(response) {
														var result = Ext.decode(response.responseText);//将后台发送的字符串转换为json对象
														if(result.obj != null) {
															Ext.getCmp("msg").setText("该供应商已存在，如果你更改下面的信息，将不可恢复。");
															//Ext.getCmp().setValue();
															//Ext.getCmp("supplierCode").setValue(result.obj.supplierCode);
															Ext.getCmp("supplierName").setValue(result.obj.supplierName);
															Ext.getCmp("legalRepresentative").setValue(result.obj.legalRepresentative);
															Ext.getCmp("attorney").setValue(result.obj.attorney);
															Ext.getCmp("mobilePhone1").setValue(result.obj.mobilePhone1);
															Ext.getCmp("mobilePhone1Name").setValue(result.obj.mobilePhone1Name);
															Ext.getCmp("mobilePhone2").setValue(result.obj.mobilePhone2);
															Ext.getCmp("mobilePhone2Name").setValue(result.obj.mobilePhone2Name);
															Ext.getCmp("fixedTelephone").setValue(result.obj.fixedTelephone);
															Ext.getCmp("fixedTelephoneName").setValue(result.obj.fixedTelephoneName);
														}else {
															Ext.getCmp("msg").setText("该供应商不存在，你可以输入信息，新增该供应商。");
															//Ext.getCmp().setValue(null);
															Ext.getCmp("supplierName").setValue(null);
															Ext.getCmp("legalRepresentative").setValue(null);
															Ext.getCmp("attorney").setValue(null);
															Ext.getCmp("mobilePhone1").setValue(null);
															Ext.getCmp("mobilePhone1Name").setValue(null);
															Ext.getCmp("mobilePhone2").setValue(null);
															Ext.getCmp("mobilePhone2Name").setValue(null);
															Ext.getCmp("fixedTelephone").setValue(null);
															Ext.getCmp("fixedTelephoneName").setValue(null);
														}
													},
													failure:function() {
														Ext.Msg.alert("获取供应商信息失败！");
													},
													params:"supplierCode=" + Ext.getCmp("ledgerContractInforPanel").getForm().findField("supplierCode").getValue()
												});
						                  }  
								     }
							},
							{
								xtype:"textfield",
								fieldLabel:"供应商名称",
								name:"supplierName",
								value:this.record.data.supplierName,
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
								fieldLabel:"法人代表",
								name:"legalRepresentative",
								value:this.record.data.legalRepresentative,
								id:"legalRepresentative",
								width : 300
							},
							{
								xtype:"textfield",
								fieldLabel:"委托代理人",
								name:"attorney",
								value:this.record.data.attorney,
								id:"attorney",
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
								fieldLabel:"移动电话(一)",
								name:"mobilePhone1",
								value:this.record.data.mobilePhone1,
								id:"mobilePhone1",
								width : 300
							},
							{
								xtype:"textfield",
								fieldLabel:"移动电话(一)姓名",
								name:"mobilePhone1Name",
								value:this.record.data.mobilePhone1Name,
								id:"mobilePhone1Name",
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
								value:this.record.data.mobilePhone2,
								id:"mobilePhone2",
								width : 300
							},
							{
								xtype:"textfield",
								fieldLabel:"移动电话(二)姓名",
								name:"mobilePhone2Name",
								value:this.record.data.mobilePhone2Name,
								id:"mobilePhone2Name",
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
								value:this.record.data.fixedTelephone,
								id:"fixedTelephone",
								width : 300
							},
							{
								xtype:"textfield",
								fieldLabel:"固定电话姓名",
								name:"fixedTelephoneName",
								value:this.record.data.fixedTelephoneName,
								id:"fixedTelephoneName",
								width : 300,
								labelWidth:100,
								labelAlign:"right"
							}
				        ]
		            }
	            ]
			}],
			
			title: "供应商基本资料"
		});
		
	}
	
});


//定义合同基本信息界面
Ext.define("ShopinDesktop.BasicInformationLCUpdateView", {
	extend: "Ext.Panel",
	
	id: "basicInformationLCUpdateView",
	panel: null,
	baseUrl: "",
	record: null,
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		
		var settlementStatusStore= new Ext.data.ArrayStore ({
			fields: ['settlementStatusCode','value'],
			data : [
				["一月可结","一月可结"],
				["二月可结","二月可结"],
				["三月可结","三月可结"],
				["四月可结","四月可结"],
				["五月可结","五月可结"],
				["六月可结","六月可结"],
				["七月可结","七月可结"],
				["八月可结","八月可结"],
				["九月可结","九月可结"],
				["十月可结","十月可结"],
				["十一月可结","十一月可结"],
				["十二月可结","十二月可结"],
				["不可结","不可结"],
				["可结","可结"],
			]
		});
		
		
		var validStore= new Ext.data.ArrayStore ({
			fields: ['validCode','value'],
			data : [
				["true","有效"],
				["false","无效"]
			]
		});
		var checkTagStore= new Ext.data.ArrayStore ({
			fields: ['checkTagCode','value'],
			data : [
				["审核已通过","审核已通过"],
				["审核未通过","审核未通过"],
				["未审核","未审核"]
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
		
		var cabinetStatusStore = new Ext.data.ArrayStore({
			fields: ['cabinetStatusCode', 'value'],
			data:[
				["在柜","在柜"],
				["撤柜","撤柜"]
			]
		});
		var clearTypeStore = new Ext.data.ArrayStore({
			fields: ['clearTypeCode', 'value'],
			data:[
				["超扣","超扣"],
				["阶梯扣","阶梯扣"],
				["销售保底","销售保底"],
				["毛利保底","毛利保底"]
			]
		});
		var decorateRulesStore = new Ext.data.ArrayStore({
			fields: ['decorateRulesCode', 'value'],
			data:[
				["true","有"],
				["false","无"]
			]
		});
		var cooperationWayStore = new Ext.data.ArrayStore({
			fields: ['cooperationWayCode', 'value'],
			data:[
				["联营","联营"],
				["工厂","工厂"],
				["集团","集团"]
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
				title: "<span style='font-weight:bold;'>合同基本信息</span>",
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
								xtype: "hiddenfield",
								name: "id",
								value: this.record.data.id
							},
							{
				            	 xtype:'container',
				            	 layout:'hbox',
				            	 items:[
				            	        {//TODO
				            	        	xtype:"datefield",
				            	        	format: 'Y',
				            	        	fieldLabel:"结算状态",
				            	        	id:'settlementStatusId0',
				            	        	name:'settlementStatusId0',
				            	        	width : 170,
				            	        },{
				            	        	xtype:"combo",
				            	        	emptyText:"请选择",
				            	        	editable:false,							//组件是否可编辑，否
				            	        	id:"settlementStatusId",				//组件id
				            	        	store: settlementStatusStore,			//组件store
				            	        	queryMode: "local",						//本地
				            	        	valueField: 'settlementStatusCode',		//组件的值
				            	        	displayField:'value',					//组件显示的列
				            	        	triggerAction : 'all',					//显示所有列表项
				            	        	name:'settlementStatus',				//组件name
				            	        	mode:'local',	 					//本地
				            	        	width : 130,
				            	        	labelAlign:"right"
				            	        }]
				               },
							{
								xtype:"textfield",
								fieldLabel:"签署标记",
								name:"signMark",
								value:this.record.data.signMark,
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
				        	//在柜状态
							{
								xtype:"combo",							//组件类型：ComboBox
								fieldLabel : '在柜状态',					//组件标签
								emptyText:"请选择",
								width : 300,
								editable:false,							//组件是否可编辑，否
								id:"cabinetStatusId",					//组件id
								store: cabinetStatusStore,				//组件store
								queryMode: "local",						//本地
								valueField: 'cabinetStatusCode',		//组件的值
								displayField:'value',					//组件显示的列
								//hiddenName:'cabinetStatus',			//隐藏表单项<input name='cabinetStatus' type='hidden'/>
								triggerAction : 'all',					//显示所有列表项
								name:'cabinetStatus',					//组件name
								mode:'local'		 					//本地
							},
							//撤柜日期
							{
								xtype:"datefield",
								format: 'Y.m.d',
								fieldLabel:"撤柜日期",
								name:"cabinetDate",
								value:this.record.data.cabinetDate,
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
								format: 'Y.m.d',
								fieldLabel:"授权开始日",
								name:"authorizedStartDate",
								value:this.record.data.authorizedStartDate,
								width : 300
							},
							
							{
								xtype:"datefield",
								format: 'Y.m.d',
								fieldLabel:"授权结束日",
								name:"authorizedEndDate",
								value:this.record.data.authorizedEndDate,
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
							mode:'local'		 			//本地
							},
							{
								xtype:"textfield",
								fieldLabel:"品牌名称",
								name:"brandName",
								value:this.record.data.brandName,
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
								fieldLabel:"门店编码",
								name:"storeEncoding",
								value:this.record.data.storeEncoding,
								width : 300
							},
							
							{
								xtype:"textfield",
								fieldLabel:"门店",
								name:"storeName",
								value:this.record.data.storeName,
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
								xtype:"numberfield",
								allowNegative:false,
								allowDecimals:true,
								fieldLabel:"扣率",
								name:"deductionRate",
								value:this.record.data.deductionRate*0.01,
								width : 300
							},
							
							{
								xtype:"textfield",
								fieldLabel:"考核指标",
								name:"assessmentIndicator",
								value:this.record.data.assessmentIndicator,
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
								format: 'Y.m.d',
								fieldLabel:"基本签订日期",
								name:"basicSigningDate",
								value:this.record.data.basicSigningDate,
								width : 300
							},
							{
								xtype:"datefield",
								format: 'Y.m.d',
								fieldLabel:"商务签订日期",
								name:"signingDate",
								value:this.record.data.signingDate,
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
								format: 'Y.m.d',
								fieldLabel:"开始日期",
								name:"startDate",
								value:this.record.data.startDate,
								width : 300
							},
							{
								xtype:"datefield",
								format: 'Y.m.d',
								fieldLabel:"结束日期",
								name:"endDate",
								value:this.record.data.endDate,
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
								format: 'Y.m.d',
								fieldLabel:"合同截止日期",
								name:"contractDeadline",
								value:this.record.data.contractDeadline,
								width : 300
							},
							{//TODO 清算类型
								xtype:"combo",							//组件类型：ComboBox
								fieldLabel : '清算类型',					//组件标签
								emptyText:"请选择",
								width : 300,
								editable:false,							//组件是否可编辑，否
								id:"clearTypeId",						//组件id
								store: clearTypeStore,					//组件store
								queryMode: "local",						//本地
								valueField: 'clearTypeCode',			//组件的值
								displayField:'value',					//组件显示的列
								triggerAction : 'all',					//显示所有列表项
								name:'clearType',						//组件name
								value:this.record.data.clearType,
								mode:'local',		 					//本地
								labelAlign:"right"
							}
				        ]
		            },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
	                	xtype:'container',
	                	layout:'hbox',
	                	items:[{
							xtype:"textfield",
							fieldLabel:"清算阈值",
							name:"clearThreshold",
							width : 300,
							value:this.record.data.clearThreshold,
							labelWidth:100,
	                	},{//清算扣率
							xtype:"textfield",
							fieldLabel:"清算扣率",
							name:"clearDeduction",
							width : 300,
							labelWidth:100,
							value:this.record.data.clearDeduction,
							labelAlign:"right"
	                	}
	                	]
	                },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
	                	xtype:'container',
	                	layout:'hbox',
	                	items:[{//商户装修手册
	                		xtype:"combo",							//组件类型：ComboBox
							fieldLabel : '商户装修手册',					//组件标签
							emptyText:"请选择",
							width : 300,
							editable:false,							//组件是否可编辑，否
							id:"decorateRulesId",						//组件id
							store: decorateRulesStore,					//组件store
							queryMode: "local",						//本地
							valueField: 'decorateRulesCode',			//组件的值
							displayField:'value',					//组件显示的列
							triggerAction : 'all',					//显示所有列表项
							name:'decorateRules',						//组件name
							value:this.record.data.decorateRules,
							mode:'local'		 					//本地
	                	},{//面积
	                		xtype:"textfield",
							fieldLabel:"面积",
							name:"area",
							width : 300,
							labelWidth:100,
							value:this.record.data.area,
							labelAlign:"right"
	                	}]
	                },{
		            	xtype: 'container',
	                    layout: 'hbox',
	                    height:5
	                },
	                {
	                	xtype:'container',
	                	layout:'hbox',
	                	items:[{//商务合同版本
	                		xtype:"textfield",
							fieldLabel:"商务合同版本",
							name:"bizContractVersion",
							value:this.record.data.bizContractVersion,
							width : 300,
							labelWidth:100
	                	},{//合作方式
	                		xtype:"combo",							//组件类型：ComboBox
							fieldLabel : '合作方式',					//组件标签
							emptyText:"请选择",
							width : 300,
							editable:false,							//组件是否可编辑，否
							id:"cooperationWayId",						//组件id
							store: cooperationWayStore,					//组件store
							queryMode: "local",						//本地
							valueField: 'cooperationWayCode',			//组件的值
							displayField:'value',					//组件显示的列
							triggerAction : 'all',					//显示所有列表项
							name:'cooperationWay',						//组件name
							value:this.record.data.cooperationWayCode,
							mode:'local',		 					//本地
							labelAlign:"right"
	                	}]
	                },
	                {
                		xtype: 'container',
                		layout: 'hbox',
                		height:5
                	},{

	                	xtype:'container',
	                	layout:'hbox',
	                	items:[{
	                		xtype:"textfield",
							fieldLabel:"品牌级别",
							name:"brandLevel",
							value:this.record.data.brandLevel,
							width : 600,
							labelWidth:100
	                	}]
	                
                	},
	                	{
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
								fieldLabel:"补充协议",
								name:"supplemental",
								value:this.record.data.supplemental
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
								/*allowNegative:false,
								allowDecimals:true,*/
								fieldLabel:"综合管理费(/元/天/平米)",
								name:"managementFees",
								value:this.record.data.managementFees,
								width : 300,
								labelWidth:100
							},
							
							{
								xtype:"textfield",
								fieldLabel:"内外卡手续费",
								name:"cardFees",
								value:this.record.data.cardFees,
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
								fieldLabel:"合同版本",
								name:"contractVersion",
								value:this.record.data.contractVersion,
								width : 300
							},
							
							{
								xtype:"combo",					//组件类型：ComboBox
								fieldLabel : '审核标记',			//组件标签
								hidden:true,
								emptyText:"请选择",
								width : 300,
								editable:false,					//组件是否可编辑，否
								id:"checkTagId",				//组件id
								store: checkTagStore,			//组件store
								queryMode: "local",				//本地
								valueField: 'checkTagCode',		//组件的值
								displayField:'value',			//组件显示的列
								//hiddenName:'checkTag',		//隐藏表单项<input name='checkTag' type='hidden'/>
								triggerAction : 'all',			//显示所有列表项
								name:'checkTag',				//组件name
								mode:'local',		 			//本地
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
								fieldLabel:"审核人",
								hidden:true,
								name:"checkName",
								value:this.record.data.checkName,
								width : 300
							},
							{
								xtype:"datefield",
								format: 'Y.m.d',
								fieldLabel:"审核日期",
								hidden:true,
								name:"checkDate",
								value:this.record.data.checkDate,
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
								fieldLabel:"备注",
								value:this.record.data.mark,
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
								fieldLabel : '是否有效',		//组件标签
								emptyText:"请选择",
								width : 300,
								editable:false,				//组件是否可编辑，否
								id:"validId",				//组件id
								store: validStore,			//组件store
								queryMode: "local",			//本地
								valueField: 'validCode',	//组件的值
								displayField:'value',		//组件显示的列
								//hiddenName:'valid',		//隐藏表单项<input name='valid' type='hidden'/>
								triggerAction : 'all',		//显示所有列表项
								name:'valid',				//组件name
								mode:'local'		 		//本地
							}
				        ]
		            }
	            ]
			}],
			
			title: "合同基本信息"
		});
		var settlementStatus = this.record.data.settlementStatus;
		var yearOfSettlementStatus=settlementStatus.substring(0,4);
		if(isNaN(yearOfSettlementStatus)){
			//不是数字
			Ext.getCmp("settlementStatusId").select(settlementStatusStore.getAt(settlementStatusStore.find('value',settlementStatus)));
		}else{
			//是数字，去掉前4位
			Ext.getCmp("settlementStatusId").select(settlementStatusStore.getAt(settlementStatusStore.find('value',settlementStatus.substring(5,settlementStatus.length))));
			Ext.getCmp("settlementStatusId0").setValue(yearOfSettlementStatus);
		}
		Ext.getCmp("cabinetStatusId").select(cabinetStatusStore.getAt(cabinetStatusStore.find('value',this.record.data.cabinetStatus)));
		Ext.getCmp("categoryId").select(categoryStore.getAt(categoryStore.find('value',this.record.data.category)));
		Ext.getCmp("checkTagId").select(checkTagStore.getAt(checkTagStore.find('value',this.record.data.checkTag)));
		Ext.getCmp("validId").select(validStore.getAt(validStore.find('validCode',this.record.data.valid)));
		Ext.getCmp("decorateRulesId").select(decorateRulesStore.getAt(decorateRulesStore.find('decorateRulesCode',this.record.data.decorateRules)));
		Ext.getCmp("cooperationWayId").select(cooperationWayStore.getAt(cooperationWayStore.find('value',this.record.data.cooperationWay)));
		Ext.getCmp("clearTypeId").select(clearTypeStore.getAt(clearTypeStore.find('value',this.record.data.clearType)));
	}
});

//定义合同文体
Ext.define("ShopinDesktop.ContractEntityLCUpdateView", {
	extend: "Ext.Panel",
	
	id: "contractEntityLCUpdateView",
	panel: null,
	baseUrl: "",
	record: null,
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		var gridCheckBox = Ext.create('Ext.selection.CheckboxModel',{//表格选择模型
					checkOnly:false//屏蔽原有的表格选择模式，只能通过复选框进行选择
		});
		
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			clicksToEdit : 2,
			//配置点击的次数来移动到一个新的行，如果它是可见的，激活编辑下一行的行编辑器。 这个值默认与 clicksToEdit 的属性值相同.
	        clicksToMoveEditor: 1,
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
		
		//构造协议历程数据
		var courseData = new Array();
		var courseJson = Ext.decode(this.record.data.courseAllInfo);
		Ext.each(courseJson, function(course){
			var courseDataItem = new Array();
			
			courseDataItem.push(course.id);
			courseDataItem.push(course.ledgerContractId);
			courseDataItem.push(course.contractReturnDate);
			courseDataItem.push(course.contractType);
			courseDataItem.push(course.contractBeginDate);
			courseDataItem.push(course.contractEndDate);
			courseDataItem.push(course.contractReviewStatus);
			courseDataItem.push(course.contractReviewer);
			courseDataItem.push(course.contractReviewDate);
			
			courseData.push(courseDataItem);
		});
		
		//审核状态
		var contractReviewStatusStore = new Ext.data.ArrayStore ({
			fields: ['contractReviewStatusCode','value'],
			data : [
				["审核通过","审核通过"],
				["审核未通过","审核未通过"],
				["未审核","未审核"]
			]
		});
		
		var courseStore = Ext.create('Ext.data.JsonStore', {
				autoLoad : false,
				fields : [
					{
						name:'courseId',
						type:'int'
					},
					{
						name:'ledgerContractId',
						type:'int'
					},
					{
						name : 'contractReturnDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					},{
						name : 'contractType',
						type : 'string'
					},{
						name : 'contractBeginDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					},{
						name : 'contractEndDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					},{
						name : 'contractReviewStatus',
						type : 'string'
					},{
						name : 'contractReviewer',
						type : 'string'
					},{
						name : 'contractReviewDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					}
				
				],
				storeId : 'courseStore',
				listeners : {
					beforeload : function(store, operation, eOpts) {
					}
				},
				data:courseData
		});
		Ext.define('courseItem', {
	        fields : [
	        		{
						name:'courseId',
						type:'int'
					},
					{
						name:'ledgerContractId',
						type:'int'
					},
	        		{
						name : 'contractReturnDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					},{
						name : 'contractType',
						type : 'string'
					},{
						name : 'contractBeginDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					},{
						name : 'contractEndDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					},{
						name : 'contractReviewStatus',
						type : 'string'
					},{
						name : 'contractReviewer',
						type : 'string'
					},{
						name : 'contractReviewDate',
						type : 'date',
						dateFormat: 'Y.m.d'
					}
				]
   		 });
		
		var coursePanel = Ext.create("Ext.grid.Panel",{
				id : 'coursePanel',
				columns : [
					{
						dataIndex:'courseId',
						text:'协议历程ID',
						hidden:true
					},
					{
						dataIndex:'ledgerContractId',
						text:'合同台账ID',
						hidden:true
					},
					{
						dataIndex : 'contractReturnDate',
						text :'合同返回日期',
						xtype: 'datecolumn',
						format:'Y.m.d',
						width:80,
						editor: {
			                 xtype:'datefield',
			                 format:'Y.m.d',
			                 editable:false
				        }
					},
					{
						dataIndex : 'contractType',
						text : '合同类型',
						width:80,
						editor: {
			                 xtype:'textfield'
				        }
					},
					{
						dataIndex : 'contractBeginDate',
						text :'合同开始日期',
						xtype: 'datecolumn',
						format:'Y.m.d',
						width:80,
						editor: {
			                 xtype:'datefield',
			                 format:'Y.m.d',
			                 editable:false
				        }
					},
					{
						dataIndex : 'contractEndDate',
						text :'合同结束日期',
						xtype: 'datecolumn',
						format:'Y.m.d',
						width:80,
						editor: {
			                 xtype:'datefield',
			                 format:'Y.m.d',
			                 editable:false
				        }
					},
					{
						dataIndex : 'contractReviewStatus',
						text : '审核状态',
						width:70
//						editor: {
//							xtype:"combo",							//组件类型：ComboBox
//							emptyText:"请选择",
//							editable:false,							//组件是否可编辑，否
//							id:"contractReviewStatusId",			//组件id
//							store: contractReviewStatusStore,		//组件数据
//							queryMode: "local",						//本地
//							valueField: 'value',					//组件的值
//							displayField:'contractReviewStatusCode',//组件显示的列
//							//hiddenName:'contractReviewStatus',	//隐藏表单项<input name='contractReviewStatus' type='hidden'/>
//							triggerAction : 'all',					//显示所有列表项
//							name:'contractReviewStatus',			//组件name
//							mode:'local',		 					//本地
//						}
					},
					{
						dataIndex : 'contractReviewer',
						text : '审核人',
						width:60
					},
					{
						dataIndex : 'contractReviewDate',
						text :'审核日期',
						xtype: 'datecolumn',
						format:'Y.m.d',
						width:80
					},
					{
					text:"操作",
					xtype:'actioncolumn',
					sortable: true,
					width:60,
					items:[
						{
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
								courseStore.remove(sm.getSelection());
							}
						},
						{
							text:'审核通过',
							xtype : 'button',
							tooltip: '审核通过',
							icon:_appctx+'/images/pass.gif',
							handler:function(grid, rowIndex, colIndex){
							
								rowEditing.cancelEdit();//取消修改状态
							
								
								
								//设置审核通过
								grid.getStore().getAt(rowIndex).set("contractReviewStatus","审核通过");
								//设置审核人
								grid.getStore().getAt(rowIndex).set("contractReviewer",username);
								//设置审核日期
								var curDate = new Date()
								var time = Ext.Date.format(curDate, 'Y.m.d'); 
								grid.getStore().getAt(rowIndex).set("contractReviewDate",time);
								
								
								//当前行已被选中，这里需要再次选中，以显示在复选框中
								//取消选中所有的行
								var sm = grid.getSelectionModel();//获取选择模型
								sm.deselect(sm.getSelection());
								//选中当前行
								sm.select(rowIndex);
							}
						},
						{
							text:'审核未通过',
							xtype : 'button',
							tooltip: '审核未通过',
							icon:_appctx+'/images/nopass.png',
							handler:function(grid, rowIndex, colIndex){
							
								rowEditing.cancelEdit();//取消修改状态
							
								
								
								//设置审核未通过
								grid.getStore().getAt(rowIndex).set("contractReviewStatus","审核未通过");
								//设置审核人
								grid.getStore().getAt(rowIndex).set("contractReviewer",username);
								//设置审核日期
								var curDate = new Date()
								var time = Ext.Date.format(curDate, 'Y.m.d'); 
								grid.getStore().getAt(rowIndex).set("contractReviewDate",time);
								
								
								
								//当前行已被选中，这里需要再次选中，以显示在复选框中
								//取消选中所有的行
								var sm = grid.getSelectionModel();//获取选择模型
								sm.deselect(sm.getSelection());
								//选中当前行
								sm.select(rowIndex);
							}
						}
					]
				}],
				 tbar: [
					{
			            text: '添加',
			            xtype : 'button',
			            handler : function() {
						 
						 	rowEditing.cancelEdit();//取消修改状态
							courseStore.insert(0, Ext.create("courseItem",{}));
							rowEditing.startEdit(0, 0);//默认编辑状态是第一行第一列
							
							//设置审核状态的默认值
							courseStore.getAt(0).set("contractReviewStatus","未审核");
							
							//取消当前新增行的选中的状态
							var sm = coursePanel.getSelectionModel();//获取选择模型
							sm.deselect(sm.getSelection());
			            }
			        },
			        //批量删除
			        {
			            text: '删除',
			            xtype : 'button',
			            hidden:true,
			            handler : function() {
						 
						 	rowEditing.cancelEdit();//取消修改状态
							
							var sm = coursePanel.getSelectionModel();//获取选择模型
							var selected = sm.selected;
							if(selected.getCount() == 0) {
								Ext.Msg.alert("提示", "请选择至少一条记录！");
							}else{
								Ext.MessageBox.confirm('确认', '你确定要进行此操作吗？',function (e){
									if(e=='yes') {

						               courseStore.remove(sm.getSelection());//删除选中行

									}
					             });
								
							}
			            }
			        },
			        //批量审核通过
			        {
			            text: '审核通过',
			            xtype : 'button',
			            handler : function() {
						 
						 	rowEditing.cancelEdit();//取消修改状态
							//批量审核通过
						 	var sm = coursePanel.getSelectionModel();//获取选择模型
							var selected = sm.selected;
							if(selected.getCount() == 0) {
								Ext.Msg.alert("提示", "请选择至少一条记录！");
							}else{
								Ext.MessageBox.confirm('确认', '你确定要进行此操作吗？',function (e){
									if(e=='yes') {
										//批量审核通过
										for (var i = 0; i < sm.getSelection().length; i++) {
											sm.getSelection()[i].set("contractReviewStatus","审核通过");
											//设置审核人
											sm.getSelection()[i].set("contractReviewer",username);
											//设置审核日期
											var curDate = new Date()
											var time = Ext.Date.format(curDate, 'Y.m.d'); 
											sm.getSelection()[i].set("contractReviewDate",time);
										}
										//当前行已被选中，这里需要再次选中，以显示在复选框中
										sm.select(sm.getSelection());
									}
					             });
								
							}
							
			            }
			        },
			        //批量审核未通过
			        {
			            text: '审核未通过',
			            xtype : 'button',
			            handler : function() {
						 
						 	rowEditing.cancelEdit();//取消修改状态
							//批量审核未通过
						 	var sm = coursePanel.getSelectionModel();//获取选择模型
							var selected = sm.selected;
							if(selected.getCount() == 0) {
								Ext.Msg.alert("提示", "请选择至少一条记录！");
							}else{
								Ext.MessageBox.confirm('确认', '你确定要进行此操作吗？',function (e){
									if(e=='yes') {

						               	//批量审核未通过
										for (var i = 0; i < sm.getSelection().length; i++) {
											sm.getSelection()[i].set("contractReviewStatus","审核未通过");
											//设置审核人
											sm.getSelection()[i].set("contractReviewer",username);
											//设置审核日期
											var curDate = new Date()
											var time = Ext.Date.format(curDate, 'Y.m.d'); 
											sm.getSelection()[i].set("contractReviewDate",time);
										}
										//当前行已被选中，这里需要再次选中，以显示在复选框中
										sm.select(sm.getSelection());
									}
					             });
								
							}
							
							
			            }
			        }
				],
		        plugins : [ rowEditing ],
				store :courseStore,
				height:300,
				columnLines : true,
				autoscroll: true,
				selModel: gridCheckBox
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
				title: "<span style='font-weight:bold;'>合同文体</span>",
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
								fieldLabel:"所有历程信息",
								hidden:true,
								name:"courseAllInfo",
								id:"courseAllInfo",
								width : 300
							},
				        	coursePanel
				        ]
		            }
	            ]
			}],
			
			title: "合同文体"
		});
	}
	
});

//定义主界面
Ext.define("ShopinDesktop.UpdateLedgerContractWindow", {
	extend: "Ext.window.Window",
	
	id: "updateLedgerContractWindow",
	panel: null,
	baseUrl: "",
	globalRecord: null,
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		
		this.initComponents();
		this.superclass.constructor.call(this, {
			width:940,
			height:480,
			modal: true,				//模式对话框
			constrain: true,			//不越出边界
			layout: "fit",
			items: [this.panel],
			title: "合同信息修改"
		});
	},
	initComponents: function() {
		var thisWindow = this;			//将当前window窗体对象赋值给thisWindow
		var globalRecord = this.globalRecord;
		var baseUrl = this.baseUrl;
		var treeStore = Ext.create("Ext.data.TreeStore",{
				root:{
					cls : 'feeds-node',
					text : '合同台账',
					expanded : true,		//展开
					children:[{
						text: "供应商基本资料",
						iconCls:'button_tree',
						leaf: true,
						id:"supplyInfoLCUpdate",
					},{
						text: "合同基本信息",
						iconCls:'button_tree',
						leaf: true,
						id:"basicInformationLCUpdate"
					},{
						text: "合同文体",
						iconCls:'button_tree',
						leaf: true,
						id:"contractEntityLCUpdate"
					}
					]
				}
			});
		
		
		//以下是初始化三个子界面，用于添加到TabPanel中
		//初始化供应商基本资料界面
		var supplyInfoLCUpdateView = Ext.create("ShopinDesktop.SupplyInfoLCUpdateView", {
											id: "supplyInfoLCUpdateView",
											baseUrl: baseUrl,
											caller: thisWindow,
											record: globalRecord,		//用户点击表格某一行所对应的record
											closable:true
										});
		//初始化合同基本信息界面
		var basicInformationLCUpdateView = Ext.create("ShopinDesktop.BasicInformationLCUpdateView", {
											id: "basicInformationLCUpdateView",
											baseUrl: baseUrl,
											caller: thisWindow,
											record: globalRecord,		//用户点击表格某一行所对应的record
											closable:true
										});
		//初始化协议历程
		var contractEntityLCUpdateView = Ext.create("ShopinDesktop.ContractEntityLCUpdateView", {
											id: "contractEntityLCUpdateView",
											baseUrl: baseUrl,
											caller: thisWindow,
											record: globalRecord,		//用户点击表格某一行所对应的record
											closable:true
										});
		this.panel = Ext.create("Ext.form.Panel", {		//form panel
			
			id:'ledgerContractInforPanel',
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
			defaultType: "textfield",					//默认的内部组件为textfield
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
 									if(newId=="supplyInfoLCUpdate"){								//点击的是供应商基本资料
									  Ext.getCmp('updateLedgerContractView').add(
										Ext.create("ShopinDesktop.SupplyInfoLCUpdateView", {
											id: "supplyInfoLCUpdateView",
											baseUrl: baseUrl,
											caller: thisWindow,
											record: globalRecord,		//用户点击表格某一行所对应的record
											closable:true
										})
									  );
									  Ext.getCmp('updateLedgerContractView').setActiveTab(Ext.getCmp("supplyInfoLCUpdateView"));
								  }else if(newId=="basicInformationLCUpdate"){	//点击的是合同基本信息
									  Ext.getCmp('updateLedgerContractView').add(
										Ext.create("ShopinDesktop.BasicInformationLCUpdateView", {
											id: "basicInformationLCUpdateView",
											baseUrl: baseUrl,
											caller: thisWindow,
											record: globalRecord,		//用户点击表格某一行所对应的record
											closable:true
										})
									  );
									  Ext.getCmp('updateLedgerContractView').setActiveTab(Ext.getCmp("basicInformationLCUpdateView"));
								  }else if(newId=="contractEntityLCUpdate"){					//点击的是协议历程
									  Ext.getCmp('updateLedgerContractView').add(
										Ext.create("ShopinDesktop.ContractEntityLCUpdateView", {
											id: "contractEntityLCUpdateView",
											baseUrl: baseUrl,
											caller: thisWindow,
											record: globalRecord,		//用户点击表格某一行所对应的record
											closable:true
										})
									  );
									  Ext.getCmp('updateLedgerContractView').setActiveTab(Ext.getCmp("contractEntityLCUpdateView"));
								  }
							 }else{
								 //如果该窗体已被创建，则判断该窗体是否已存在于TabPanel中。
								 if(isExitTab(tabId)) {
									 //如果该窗体已存在于TabPanel中，则激活。
								 	Ext.getCmp('updateLedgerContractView').setActiveTab(tab);
								 }else {
									 //如果该窗体不存在于TabPanel中，则将该窗体添加到TabPanel中，并激活。
									Ext.getCmp('updateLedgerContractView').add(tab);
									Ext.getCmp('updateLedgerContractView').setActiveTab(tab);
								 }
							 }
							  
							 
							 //判断该id的tab是否存在于TabPanel中
							 function isExitTab(tabId) {
								 //获取tab容器组件 
								 var tabsPanel = Ext.getCmp('updateLedgerContractView');
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
						id:"updateLedgerContractView",
						width: "80%",
						region:'center',
						activeTab: 0,
						enableTabScroll : true,
						autoScroll : true,						
			 			frame:true, 
			 			items:[
			 				supplyInfoLCUpdateView,
			 				basicInformationLCUpdateView,
			 				contractEntityLCUpdateView
			 			]
					})
			       ]
		});
		
		
			
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var editForm = this.panel.getForm();
		var baseUrl = this.baseUrl;
		if (editForm.isValid()) {
			
			
			var coursePanel = Ext.getCmp('coursePanel');
			//alert(coursePanel);
			var courseStore=Ext.getCmp('coursePanel').getStore();//获取协议历程信息store
			//alert(courseStore);
			var courseArr= new Array();//创建一个存储协议历程的数组
			var jsonCourse = "";//创建一个存储协议历程的json字符串
			var start = "[";
			var end = "]";
			courseStore.each(function(record){
            	jsonCourse = "{id:'"+record.get('courseId')+"',ledgerContractId:'"+record.get('ledgerContractId')+"',courseTime:'"+record.get('courseTime')+"',courseContent:'"+record.get('courseContent')+"',contractReturnDate:'"+record.get('contractReturnDate')+"',contractType:'"+record.get('contractType')+"',contractBeginDate:'"+record.get('contractBeginDate')+"',contractEndDate:'"+record.get('contractEndDate')+"',contractReviewStatus:'"+record.get('contractReviewStatus')+"',contractReviewer:'"+record.get('contractReviewer')+"',contractReviewDate:'"+record.get('contractReviewDate')+"'}";
                courseArr.push(jsonCourse);
            });
            editForm.findField('courseAllInfo').setValue(start+courseArr+end);
			//alert(start+courseArr+end);
			
			
			
			editForm.submit({
				url: baseUrl + "/ledgerContract/updateLedgerContractCustom.json",
				success: function(form, action) {
					Ext.MessageBox.alert("Success", "保存成功", function() {
						Ext.getCmp('ledgerContractView').getStore().load();
						thisWindow.close();
						caller.pageBar.doRefresh();//caller和Ext.getCmp('ledgerContractView')相同
					});
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