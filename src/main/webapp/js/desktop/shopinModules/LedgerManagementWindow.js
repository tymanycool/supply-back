/**
 * 台账管理：资质台账、合同台账
 * author liuchunlong
 */
//绘制带有综合查询功能的合同台账界面 
Ext.define("ShopinDesktop.LedgerContractSearchView",{
	extend:"Ext.panel.Panel",
	searchPanel:null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'ledgerContractSearchView',
			layout:'border',
			items:[
				this.searchPanel,
				Ext.create("ShopinDesktop.LedgerContractView", {
					id: "ledgerContractView",
					baseUrl: _appctx,
					region:'center'
				})
			]
		});
	},
	initComponents: function() {
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
		var statusStore = new Ext.data.ArrayStore({
			fields:['statusId','value'],
			data:[
			      ["在柜","在柜"],
			      ["撤柜","撤柜"]
			  ]
		});
		this.searchPanel = Ext.create("Ext.panel.Panel",{
			region:'north',
			animCollapse: true,
			collapsible: true,
        	collapsed: true,
			title:'综合搜索',
			layout:{
				type:'vbox',
				padding:'5',
				align:"stretch"
			},
			defaults:{
				margins:'0 0 5 0'
			},
			items :[
				Ext.create("Ext.panel.Panel",{
					flex:1,
					animCollapse: true,
					collapsible: true,
		        	collapsed: true,
					title:'基本搜索',
					layout:'anchor',
					defaults:{
						anchor:'100%',
						padding:'0 5 0 5'
					},
					items:[
						
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
										xtype:"textfield",
										fieldLabel:"供应商编码",
										name:"supplierCode",
										id:"supplierCodeOfLedgerContractSearch",
										flex:1,
										labelWidth:70,
									},
									{
										xtype:"textfield",
										fieldLabel:"供应商名称",
										name:"supplierName",
										id:"supplierNameOfLedgerContractSearch",
										flex:1,
										labelWidth:70,
									},
									{
										xtype:"textfield",
										fieldLabel:"品牌名称",
										name:"brandName",
										id:"brandNameOfLedgerContractSearch",
										flex:1,
										labelWidth:70,
									},
									{
										xtype:"combo",					//组件类型：ComboBox,下拉列表框
										emptyText:"请选择",
										fieldLabel : '品类',				//组件标签
										editable:true,					//组件是否可编辑，否
										id:"categoryOfLedgerContractSearch",				//组件id
										store: categoryStore,			//组件数据
										queryMode: "local",				//本地
										valueField: 'value',			//组件的值
										displayField:'categoryCode',	//组件显示的列
										//hiddenName:'category',		//隐藏表单项<input name='category' type='hidden'/>
										triggerAction : 'all',			//显示所有列表项
										name:'category',				//组件name
										labelWidth:70,
										mode:'local'		 			//本地
									},
							    ]
				            },{
				            	xtype: 'container',
						        layout: 'hbox',
						        items:[
						        	{
									xtype:"datefield",
									format: 'Y-m-d',
									fieldLabel:"合同生效日期",
									name:"contractStartDate",
									id:"contractStartDateOfLedgerContractSearch",
									width : 200,
									labelAlign:"left",
									labelWidth:100,
									},{
										xtype:"datefield",
										format: 'Y-m-d',
										fieldLabel:"到",
										name:"contractStartDate2",
										id:"contractStartDateOfLedgerContractSearch2",
										width : 120,
										labelWidth:20,
									},
									{
									xtype:"datefield",
									format: 'Y-m-d',
									fieldLabel:"合同失效日期",
									id:"contractEndDateOfLedgerContractSearch",
									name:"contractEndDate",
									width : 200,
									labelWidth:100,
									labelAlign:"right"
									},{
										xtype:"datefield",
										format: 'Y-m-d',
										fieldLabel:"到",
										id:"contractEndDateOfLedgerContractSearch2",
										name:"contractEndDate2",
										width : 120,
										labelWidth:20,
										labelAlign:"right"
									},{
										xtype:"combo",					//组件类型：ComboBox,下拉列表框
										emptyText:"请选择",
										fieldLabel : '在柜状态',				//组件标签
										editable:true,					//组件是否可编辑，否
										id:"statusOfLedgerContractSearch",				//组件id
										store: statusStore,			//组件数据
										queryMode: "local",				//本地
										valueField: 'value',			//组件的值
										displayField:'statusId',		//组件显示的列
										triggerAction : 'all',			//显示所有列表项
										name:'status',				//组件name
										width : 200,
										labelWidth:100,
										mode:'local'		 			//本地
									}
						        ]
				            },
				            {//TODO 单选：差异性扣率
				            	xtype: 'container',
						        layout: 'hbox',
						        items:[
									{
									xtype:"radiogroup",
									columns:1,
									fieldLabel:"",
									items:[
									       {boxLabel:'合同台帐导出',id:"contraExport",name:'c-btn-1',checked:true},
									       {boxLabel:'差异性扣率导出（同一供应商同品牌不同门店）',id:"diffDeductionRate1",name:'c-btn-1'},
									       {boxLabel:'差异性扣率导出（同品牌不同供应商）',id:"diffDeductionRate2",name:'c-btn-1'},
									       {boxLabel:'品牌扣率导出（在某一范围内的品牌扣率）',id:"diffDeductionRate3",name:'c-btn-1'}
									      ]
									}
						        ]
				            },{
				            	xtype: 'container',
						        layout: 'hbox',
				            	items:[{
									xtype:"numberfield",
									allowNegative:false,
									allowDecimals:true,
									fieldLabel:"从",
									id:"rangeStart",
									width : 70,
									labelWidth:20
								}
								,{
									xtype:"numberfield",
									allowNegative:false,
									allowDecimals:true,
									fieldLabel:"到",
									id:"rangeEnd",
									width : 70,
									labelWidth:20
								}]
				            },
				            {
				            	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },
							{
				            	xtype: 'container',
						        layout:{
									type:'hbox',
									padding:'5',
									align:"middle"
								},
						        items:[
						        	{
						        		xtype:"label",
						        		text:'',
						        		flex:3
						        	},
									{
										flex:1,
										text:'查找',
										xtype:'button',
										width:80,
										pressed: true,
										//ctCls: 'x-btn-over',
										handler:function(){
										
						        		//设置查询条件
						        		var supplierCode = Ext.getCmp("supplierCodeOfLedgerContractSearch").getValue();
						        		var supplierName = Ext.getCmp("supplierNameOfLedgerContractSearch").getValue();
						        		var brandName = Ext.getCmp("brandNameOfLedgerContractSearch").getValue();
						        		var category = Ext.getCmp("categoryOfLedgerContractSearch").getValue();
						        		var contractStartDate = Ext.getCmp("contractStartDateOfLedgerContractSearch").getValue();
						        		var contractStartDate2 = Ext.getCmp("contractStartDateOfLedgerContractSearch2").getValue();
						        		var contractEndDate = Ext.getCmp("contractEndDateOfLedgerContractSearch").getValue();
						        		var contractEndDate2 = Ext.getCmp("contractEndDateOfLedgerContractSearch2").getValue();
						        		var status = Ext.getCmp("statusOfLedgerContractSearch").getValue();
						        		var extraParams = {
						        			supplierCode:supplierCode,
						        			supplierName:supplierName,
						        			brandName:brandName,
						        			category:category,
						        			//按合同生效失效日期查询
						        			contractStartDate:contractStartDate,
						        			contractStartDate2:contractStartDate2,
						        			contractEndDate:contractEndDate,
						        			contractEndDate2:contractEndDate2,
						        			status:status
						        		}
						        		Ext.apply(Ext.getCmp("ledgerContractView").getStore().getProxy().extraParams, extraParams);
						        		
						        		
								        //store跳转第一页
								        Ext.getCmp("ledgerContractView").getStore().currentPage = 1;
								        
								     	//store重新加载
								        Ext.getCmp("ledgerContractView").getStore().load();
								        
										}
									},{//TODO 重置功能
										flex:1,
										text:'重置',
										xtype:'button',
										width:80,
										pressed: true,
										//ctCls: 'x-btn-over',
										handler:function(){
											//Ext.getCmp("").setValue(null);
											Ext.getCmp("supplierCodeOfLedgerContractSearch").setValue(null);
											Ext.getCmp("supplierNameOfLedgerContractSearch").setValue(null);
											Ext.getCmp("brandNameOfLedgerContractSearch").setValue(null);
											Ext.getCmp("categoryOfLedgerContractSearch").setValue(null);
											Ext.getCmp("contractStartDateOfLedgerContractSearch").setValue(null);
											Ext.getCmp("contractEndDateOfLedgerContractSearch").setValue(null);
											Ext.getCmp("contractStartDateOfLedgerContractSearch2").setValue(null);
											Ext.getCmp("contractEndDateOfLedgerContractSearch2").setValue(null);
											Ext.getCmp("statusOfLedgerContractSearch").setValue(null);
										}
									},
									{//TODO 导出-合同台帐
										flex:1,
										text:'导出',
										xtype:'button',
										width:80,
										pressed: true,
										handler:function(){
											var rangeStart = Ext.getCmp("rangeStart").getValue();
											var rangeEnd = Ext.getCmp("rangeEnd").getValue();
											var excelNameArray = ['合同台帐.xls','差异性扣率导出（同一供应商同品牌不同门店）.xls','差异性扣率导出（同品牌不同供应商）.xls','品牌扣率导出（在某一范围内的品牌扣率）.xls'];
											var linkArray = ['exportExcelTable.json','exportExcelTable1.json','exportExcelTable2.json','exportExcelTable3.json?rangeStart='+rangeStart+"&rangeEnd="+rangeEnd];
											var contraExport = Ext.getCmp("contraExport").getValue();
											var diffDeductionRate1 = Ext.getCmp("diffDeductionRate1").getValue();
											var diffDeductionRate2 = Ext.getCmp("diffDeductionRate2").getValue();
											var diffDeductionRate3 = Ext.getCmp("diffDeductionRate3").getValue();
											var excelName;
											var linkTo;
											if(true == diffDeductionRate1){
												excelName = excelNameArray[1];
												linkTo = linkArray[1];
											}else if(true == diffDeductionRate2){
												excelName = excelNameArray[2];
												linkTo = linkArray[2];
											}else if(true == diffDeductionRate3){
												excelName = excelNameArray[3];
												linkTo = linkArray[3];
											}else if(true==contraExport){
												excelName = excelNameArray[0];
												linkTo = linkArray[0];
											}else{
												alert("请选择导出条件！");
												return;
											}
											var a = document.createElement('a');
											document.body.appendChild(a);
											a.href = _appctx+"/ledgerContract/"+linkTo;
											a.target = '_blank';
											a.download = excelName;
											a.click();
										}
									},
									{
						        		xtype:"label",
						        		text:'',
						        		flex:3
						        	},
						        ]
				            },{
				            	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                }
						
						
					]
				})	
			]
		});
	}
});

//绘制带有综合查询功能的资质台账界面 
Ext.define("ShopinDesktop.LedgerQualificationSearchView",{
	extend:"Ext.panel.Panel",
	searchPanel:null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'ledgerQualificationSearchView',
			layout:'border',
			items:[
				this.searchPanel,
				Ext.create("ShopinDesktop.LedgerQualificationView", {
					id: "ledgerQualificationView",
					baseUrl: _appctx,
					region:'center'
				})
			]
		});
	},
	
	initComponents: function() {
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
		var statusStore = new Ext.data.ArrayStore({
			fields:['statusId','value'],
			data:[
			      ["在柜","在柜"],
			      ["撤柜","撤柜"]
			  ]
		});
		this.searchPanel = Ext.create("Ext.panel.Panel",{
			region:'north',
			animCollapse: true,
			collapsible: true,
        	collapsed: true,
			title:'综合搜索',
			layout:{
				type:'vbox',
				padding:'5',
				align:"stretch"
			},
			defaults:{
				margins:'0 0 5 0'
			},
			items :[
				Ext.create("Ext.panel.Panel",{
					flex:1,
					animCollapse: true,
					collapsible: true,
		        	collapsed: true,
					title:'基本搜索',
					layout:'anchor',
					defaults:{
						anchor:'100%',
						padding:'0 5 0 5'
					},
					items:[
						
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
										xtype:"textfield",
										fieldLabel:"供应商编码",
										name:"supplierCode",
										id:"supplierCodeOfLedgerQualificationSearch",
										flex:1,
										labelWidth:70,
									},
									{
										xtype:"textfield",
										fieldLabel:"供应商名称",
										name:"supplierName",
										id:"supplierNameOfLedgerQualificationSearch",
										flex:1,
										labelWidth:70,
									},
									{
										xtype:"textfield",
										fieldLabel:"品牌名称",
										name:"brandName",
										id:"brandNameOfLedgerQualificationSearch",
										flex:1,
										labelWidth:70,
									},
									{
										xtype:"combo",					//组件类型：ComboBox,下拉列表框
										emptyText:"请选择",
										fieldLabel : '品类',				//组件标签
										editable:true,					//组件是否可编辑，否
										id:"categoryOfLedgerQualificationSearch",				//组件id
										store: categoryStore,			//组件数据
										queryMode: "local",				//本地
										valueField: 'value',			//组件的值
										displayField:'categoryCode',	//组件显示的列
										//hiddenName:'category',		//隐藏表单项<input name='category' type='hidden'/>
										triggerAction : 'all',			//显示所有列表项
										name:'category',				//组件name
										labelWidth:70,
										mode:'local'		 			//本地
									},
							    ]
				            },
				            {
				            	xtype: 'container',
						        layout: 'hbox',
						        items:[
									{
									xtype:"datefield",
									format: 'Y-m-d',
									fieldLabel:"合同失效日期",
									id:"contractEndDateOfLedgerQualificationSearch",
									name:"contractQualificationEndDate",
									width : 200,
									labelWidth:100,
									},
									{
										xtype:"combo",					//组件类型：ComboBox,下拉列表框
										emptyText:"请选择",
										fieldLabel : '在柜状态',				//组件标签
										editable:true,					//组件是否可编辑，否
										id:"statusOfLedgerQualificationSearch",				//组件id
										store: statusStore,			//组件数据
										queryMode: "local",				//本地
										valueField: 'value',			//组件的值
										displayField:'statusId',		//组件显示的列
										triggerAction : 'all',			//显示所有列表项
										name:'status',				//组件name
										width : 200,
										labelWidth:100,
										mode:'local'		 			//本地
									}
						        ]
				            },
				            {//TODO 单选：因资质不能结算明细
				            	xtype: 'container',
						        layout: 'hbox',
						        items:[
									{
									xtype:"radiogroup",
									columns:1,
									fieldLabel:"",
									items:[
									       {boxLabel:'资质台帐导出(导出查找后的结果)',id:"qualiExport",name:'q-btn-1',checked:true},
									       {boxLabel:'因资质不能结算明细导出',id:"isCheckedForQualiError",name:'q-btn-1'},
									       {boxLabel:'代理商级别表导出',id:"actingLevelExport",name:'q-btn-1'}
									      ]
									}
						        ]
				            },
				            {
				            	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },
							{
				            	xtype: 'container',
						        layout:{
									type:'hbox',
									padding:'5',
									align:"middle"
								},
						        items:[
						        	{
						        		xtype:"label",
						        		text:'',
						        		flex:3
						        	},
									{
										flex:1,
										text:'查找',
										xtype:'button',
										width:80,
										pressed: true,
										//ctCls: 'x-btn-over',
										handler:function(){
										
						        		//设置查询条件
						        		var supplierCode = Ext.getCmp("supplierCodeOfLedgerQualificationSearch").getValue();
						        		var supplierName = Ext.getCmp("supplierNameOfLedgerQualificationSearch").getValue();
						        		var brandName = Ext.getCmp("brandNameOfLedgerQualificationSearch").getValue();
						        		var category = Ext.getCmp("categoryOfLedgerQualificationSearch").getValue();
						        		var endDate = Ext.getCmp("contractEndDateOfLedgerQualificationSearch").getValue();
						        		var status = Ext.getCmp("statusOfLedgerQualificationSearch").getValue();
						        		var extraParams = {
						        			supplierCode:supplierCode,
						        			supplierName:supplierName,
						        			brandName:brandName,
						        			category:category,
						        			endDate:endDate,
						        			status:status
						        		}
						        		Ext.apply(Ext.getCmp("ledgerQualificationView").getStore().getProxy().extraParams, extraParams);
						        		
						        		
								        //store跳转第一页
								        Ext.getCmp("ledgerQualificationView").getStore().currentPage = 1;
								        
								     	//store重新加载
								        Ext.getCmp("ledgerQualificationView").getStore().load();
								        
										}
									},{
										flex:1,
										text:'重置',
										xtype:'button',
										width:80,
										pressed: true,
										//ctCls: 'x-btn-over',
										handler:function(){
											//Ext.getCmp("").setValue(null);
											Ext.getCmp("supplierCodeOfLedgerQualificationSearch").setValue(null);
							        		Ext.getCmp("supplierNameOfLedgerQualificationSearch").setValue(null);
							        		Ext.getCmp("brandNameOfLedgerQualificationSearch").setValue(null);
							        		Ext.getCmp("categoryOfLedgerQualificationSearch").setValue(null);
							        		Ext.getCmp("contractEndDateOfLedgerQualificationSearch").setValue(null);
							        		Ext.getCmp("statusOfLedgerQualificationSearch").setValue(null);
										}
									},
									{//TODO 导出-资质台帐
										flex:1,
										text:'导出',
										xtype:'button',
										width:80,
										pressed: true,
										handler:function(){
											var excelNameArray = ['资质台帐.xls','因资质不能结算明细.xls','代理商级别表.xls'];
											var linkArray = ['exportExcelTable.json','exportExcelTable1.json','exportExcelTable2.json'];
											var isCheckedForQualiError = Ext.getCmp("isCheckedForQualiError").getValue();
											var qualiExport = Ext.getCmp("qualiExport").getValue();
											var actingLevelExport = Ext.getCmp("actingLevelExport").getValue();
											var excelName;
											var linkTo;
											if(true == isCheckedForQualiError){
												excelName = excelNameArray[1];
												linkTo = linkArray[1];
											}else if(true==qualiExport){
												excelName = excelNameArray[0];
												linkTo = linkArray[0];
											}else if(true==actingLevelExport){
												excelName = excelNameArray[2];
												linkTo = linkArray[2];
											}else{
												alert("请选择导出条件！");
												return;
											}
											var a = document.createElement('a');
											document.body.appendChild(a);
											a.href = _appctx+"/ledgerQualification/"+linkTo;
											a.target = '_blank';
											a.download = excelName;
											a.click();
										}
									},
									{
						        		xtype:"label",
						        		text:'',
						        		flex:3
						        	},
						        ]
				            },{
				            	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                }
						
						
					]
				})	
			]
		});
	}
});

Ext.define('ShopinDesktop.LedgerManagementWindow', {
	extend: 'Ext.ux.desktop.Module',
	requires: [
		'Ext.data.ArrayStore',
		'Ext.util.Format',
		'Ext.grid.Panel',
		'Ext.panel.Panel',
		'Ext.TabPanel',
		'Ext.grid.RowNumberer',
		'Util.MonthField'
	],
	id:'ledgerManagementWindow',
	init : function(){
	
		this.callParent();
	},

	createWindow : function(){
		var desktop = this.app.getDesktop();		//this.app  why？
		var win = desktop.getWindow('ledgerManagementWindow');
		
		if(!win){
			var treeStore = Ext.create("Ext.data.TreeStore",{
				root:{
					cls : 'feeds-node',
					text : '用户权限管理',
					expanded : true,
					children:[{
						text: "资质台账管理",
						iconCls:'button_tree',
						leaf: true,
						id:"ledgerQualification",
					},{
						text: "合同台账管理",
						iconCls:'button_tree',
						leaf: true,
						id:"ledgerContract"
					}
					]
				},
			});
			win = desktop.createWindow({
				id: 'ledgerManagementWindow',
				title:'台账管理',
				width:940,
				height:480,
				iconCls: 'brand-icon',
				animCollapse:false,
				constrainHeader:true,
				layout: 'border',
				items: [Ext.create("Ext.tree.TreePanel",{
					title : '导航管理',
			        region:'west',
			        split:true,
			        width: "20%",
			        collapsible:true,
			        store : treeStore,
			        rootVisible : false,
					listeners:{
						  itemclick:function(view, record,item){
							  var newId = record.data.id;			//导航管理树形结构的叶子节点
							  var oldId = Ext.getCmp(newId+'SearchView');	//导航管理树形结构的叶子节点点击打开的对应的界面窗体
							  if (oldId == undefined) {
 									if(newId=="ledgerQualification"){
									  Ext.getCmp('ledgerManagementView').add(
										
										  Ext.create("ShopinDesktop.LedgerQualificationSearchView", {
											id : 'ledgerQualificationSearchView',
											title:'资质台账信息',
											closable:true,
										})
									  );
									  Ext.getCmp('ledgerManagementView').setActiveTab(Ext.getCmp("ledgerQualificationSearchView"));
									  
									  
									  
									  //气泡显示质检信息（行显示）
//								      var view = Ext.getCmp("ledgerQualificationView").getView();
//								      var tip = Ext.create('Ext.tip.ToolTip', {
//								          // The overall target element.
//								          target: view.el,
//								          // Each grid row causes its own separate show and hide.
//								          delegate: view.itemSelector,
//								          // Moving within the row should not hide the tip.
//								          trackMouse: true,
//								          // Render immediately so that tip.body can be referenced prior to the first show.
//								          renderTo: Ext.getBody(),
//								          listeners: {
//								              // Change content dynamically depending on which element triggered the show.
//								              beforeshow: function updateTipBody(tip) {
//								    	  			var inspectionRecord = view.getRecord(tip.triggerElement);
//													//构造质检报告数据
//													var inspectionData = new Array();
//													var inspectionJson = Ext.decode(inspectionRecord.data.inspectionAllInfo);
//													Ext.each(inspectionJson, function(inspection){
//														var inspectionDataItem = new Array();
//														//inspectionDataItem.push(inspection.id);
//														//inspectionDataItem.push(inspection.ledgerQualificationId);
//														inspectionDataItem.push(inspection.inspectionType);
//														inspectionDataItem.push(inspection.inspectionDate);
//														inspectionDataItem.push(inspection.inspectionContent);
//														inspectionData.push(inspectionDataItem);
//													});
//													var html = "无质检信息";
//													if(inspectionData.length > 0) {
//														html = "<table>";
//														html = html + "<thead><tr>" +
//														"<td style='border:solid 1px;'>质检类型</td>" +
//														"<td style='border:solid 1px;'>质检有效期</td>" +
//														"<td style='border:solid 1px;'>质检内容</td>" +
//														"</tr></thead><tbody>";
//														Ext.each(inspectionData, function(item) {
//															html = html + "<tr>";
//															Ext.each(item, function(value) {
//																if(value == null || value == 'undefined')
//																	value = "";
//																html = html + "<td style='border:solid 1px;'>" + value + "</td>";
//															});
//															html = html + "</tr>";
//														});
//														html = html + "</tbody></table>";
//													}
//								                  	tip.update(html);
//								              }
//								          }
//								      });
									  
								  }else if(newId=="ledgerContract"){
									  
									  Ext.getCmp('ledgerManagementView').add(
										  
										Ext.create("ShopinDesktop.LedgerContractSearchView", {
											id : 'ledgerContractSearchView',
											title:'合同台账信息',
											closable:true,
										})
									  );
									  Ext.getCmp('ledgerManagementView').setActiveTab(Ext.getCmp("ledgerContractSearchView"));
								  }
							 }else{
								 Ext.getCmp('ledgerManagementView').setActiveTab(oldId);
							 }
						  }
					}
			       }),Ext.create("Ext.TabPanel",{
						id:"ledgerManagementView",
						width: "80%",
						region:'center',
						activeTab: 0,
						enableTabScroll : true,
						autoScroll : false,						
			 			frame:true, 
			 			items:[{
			 				
			 			}
			 			]
				})
				        ]
			});
		}
		return win;
	}
});

