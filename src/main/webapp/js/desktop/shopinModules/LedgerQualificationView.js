/*
 * 资质台账信息
 * author liuchunlong
 */
Ext.define("ShopinDesktop.LedgerQualificationView",{
	extend:"Ext.grid.Panel",
	baseUrl: "",
	tbar:null,
	columns:null,
	pageBar:null,
	userInforStore:null,
	gridCheckBox:null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'ledgerQualificationView',
			columns: this.columns,
			tbar: this.tbar,
			bbar : this.pageBar,
			store: this.userInforStore,
			selModel: this.gridCheckBox,
			viewConfig : {
				enableTextSelection : true,
				getRowClass: function (record, rowIndex, rowParams, store) {
	                	if(record.data.valid) {
	                		
	                		if(record.data.isblacklist) {//如果是黑名单，背景色为灰色          		
		                    	return "row-black"; 
		                	}else if(!record.data.isblacklist) {//如果不是黑名单，则根据在柜状态进行设置字体颜色
		                		if(record.data.status == '撤柜') {
		                			return "row-status";
		                		}else {
		                			return "";
		                		}
		                	}
	                	} else {
	                		return "row-valid";
	                	}
	            }
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		this.userInforStore = Ext.create("Ext.data.Store",{
			autoLoad : false,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
				   {name:"id"},
				   {name:"inspectionAllInfo"},
				   {name:"expirationDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"status"},
				   {name:"mobilePhone1"},
				   {name:"mobilePhone1Name"},
				   {name:"mobilePhone2"},
				   {name:"mobilePhone2Name"},
				   {name:"fixedTelephone"},
				   {name:"fixedTelephoneName"},
				   {name:"brandLevel"},
				   {name:"supplierCode"},
				   {name:"supplierName"},
				   {name:"businessLicense"},
				   {name:"nationalTaxRegistration"},
				   {name:"landTaxRegistration"},
				   {name:"generalTaxpayer"},
				   {name:"isblacklist"},
				   {name:"category"},
				   {name:"brandName"},
				   {name:"registrationNumber"},
				   {name:"approvedCategory"},
				   {name:"endDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"applicationDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"trademarkHolderName"},
				   {name:"certificateType"},
				   {name:"certificateNumber"},
				   {name:"singleStoreAuthorization"},
				   {name:"actingLevel"},
				   {name:"firstProxyDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"firstProxyAnnotation"},
				   {name:"secondProxyDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"secondProxyAnnotation"},
				   {name:"thirdProxyDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"thirdProxyAnnotation"},
				   {name:"forthProxyDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"forthProxyAnnotation"},
				   {name:"fifthProxyDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"fifthProxyAnnotation"},
				   {name:"sixthProxyDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"sixthProxyAnnotation"},
				   {name:"declaration"},
				   {name:"descriptionProblem"},
				   {name:"informationMaintenanceDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"informationMaintenanceName"},
				   {name:"approvalStatus"},
				   {name:"reviewDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				   {name:"reviewName"},
				   {name:"mark"},
				   {name:"valid"}
					],
 			proxy: {
				type: "ajax",
				url : baseUrl + "/ledgerQualification/selectLedgerQualificationCustomList.json",
				getMethod: function(){
					return 'POST';
				},
				reader: {
					type: "json",
					root : "list",
					totalProperty :"total"
				},
				listeners: {
					exception: function(proxy, response, operation, eOpts) {
						if (response.status != 200) {
							Ext.MessageBox.alert("Error", "加载列表失败！");
						}
					}
				}
			}

		});

			
		this.gridCheckBox = Ext.create('Ext.selection.CheckboxModel',{//表格选择模型
					checkOnly:false//屏蔽原有的表格选择模式，只能通过复选框进行选择
		});
    	
		var pointsStore = this.userInforStore;
		var ledgerQualificationViewGridPanel = this;
		this.columns=[
				new Ext.grid.RowNumberer({
							width:40
				}),
				{
					header : "ID",
					dataIndex : "id",
					hidden : true,
					width:50
				},
				{
					header : "质检报告",
					dataIndex : "inspectionAllInfo",
					hidden : true,
					width:50
				},
				{
					header : "工作联系单截止日期",
					dataIndex : "expirationDate",
					width:110,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "状态",
					dataIndex : "status",
					width:60
				},
//				{
//					header : "移动电话(一)",
//					dataIndex : "mobilePhone1",
//					width:120
//				},
//				{
//					header : "移动电话(一)姓名",
//					dataIndex : "mobilePhone1Name",
//					width:100
//				},
//				{
//					header : "移动电话(二)",
//					dataIndex : "mobilePhone2",
//					width:120
//				},
//				{
//					header : "移动电话(二)姓名",
//					dataIndex : "mobilePhone2Name",
//					width:100
//				},
//				{
//					header : "固定电话",
//					dataIndex : "fixedTelephone",
//					width:120
//				},
//				{
//					header : "固定电话姓名",
//					dataIndex : "fixedTelephoneName",
//					width:80
//				},
				{
					header : "联系方式",
					dataIndex : "contactToolTip",
					width:60,
					align:'center',
					renderer: function(value,metaData,record,colIndex,store,view){
						var html = "没有联系方式";
						if((record.data.mobilePhone1 != null && record.data.mobilePhone1 != 'undefined' && record.data.mobilePhone1 != 'null') || (record.data.mobilePhone2 != null && record.data.mobilePhone2 != 'undefined' && record.data.mobilePhone2 != 'null') || (record.data.fixedTelephone != null && record.data.fixedTelephone != 'undefined' && record.data.fixedTelephone != 'null')) {
							html = "<table>" +
							"<thead>" +
							"<tr><td style='border:solid 1px;'>联系电话</td><td style='border:solid 1px;'>联系人</td></tr>" +
							"</thead>" +
							"<tbody>" +
							"<tr><td style='border:solid 1px;'>"+record.data.mobilePhone1+"</td><td style='border:solid 1px;'>"+record.data.mobilePhone1Name+"</td></tr>" +
							"<tr><td style='border:solid 1px;'>"+record.data.mobilePhone2+"</td><td style='border:solid 1px;'>"+record.data.mobilePhone2Name+"</td></tr>" +
							"<tr><td style='border:solid 1px;'>"+record.data.fixedTelephone+"</td><td style='border:solid 1px;'>"+record.data.fixedTelephoneName+"</td></tr>" +
							"</tbody>" +
							"</table>";
						}
						metaData.tdAttr = 'data-qtip="'+html+'"';
						return "<img src='" + _appctx + '/images/inspection.png' + "'/>"; 
					}
				},
				{
					header : "品牌级别",
					dataIndex : "brandLevel",
					width:60
				},
				{
					header : "供应商编码",
					dataIndex : "supplierCode",
					width:70
				},
				{
					header : "供应商名称",
					dataIndex : "supplierName",
					width:200
				},
				{
					header : "营业执照",
					dataIndex : "businessLicense",
					width:80
				},
				{
					header : "国税登记证",
					dataIndex : "nationalTaxRegistration",
					width:70,
					renderer:function(value) {
						if(value == true) {
							return '有';
						} else if(value == false) {
							return '无';
						}
					}
				},
				{
					header : "地税登记证",
					dataIndex : "landTaxRegistration",
					width:70,
					renderer:function(value) {
						if(value == true) {
							return '有';
						} else if(value == false) {
							return '无';
						}
					}
				},
				{
					header : "一般纳税人",
					dataIndex : "generalTaxpayer",
					width:70
				},
				{
					header : "黑名单",
					dataIndex : "isblacklist",
					width:50,
					renderer:function(value) {
					
						if(value == true) {
							return '是';
						} else if(value == false) {
							return '否';
						}
					}
				},
				{
					header : "品类",
					dataIndex : "category",
					width:50
				},
				{
					header : "品牌名称",
					dataIndex : "brandName",
					width:150
				},
				{
					header : "注册证号码",
					dataIndex : "registrationNumber",
					width:70
				},
				{
					header : "核定类别",
					dataIndex : "approvedCategory",
					width:60
				},
				{
					header : "结束日期",
					dataIndex : "endDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "申请日期",
					dataIndex : "applicationDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "个人商标持有人姓名",
					dataIndex : "trademarkHolderName",
					width:120
				},
				{
					header : "证件类型",
					dataIndex : "certificateType",
					width:60
				},
				{
					header : "证件号码",
					dataIndex : "certificateNumber",
					width:150
				},
				{
					header : "单店授权",
					dataIndex : "singleStoreAuthorization",
					width:200
				},
				{
					header : "代理级别",
					dataIndex : "actingLevel",
					width:60
				},
				{
					header : "一级代理-日期",
					dataIndex : "firstProxyDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "一级代理-批注",
					dataIndex : "firstProxyAnnotation",
					width:150
				},
				{
					header : "二级代理-日期",
					dataIndex : "secondProxyDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "二级代理-批注",
					dataIndex : "secondProxyAnnotation",
					width:150
				},
				{
					header : "三级代理-日期",
					dataIndex : "thirdProxyDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "三级代理-批注",
					dataIndex : "thirdProxyAnnotation",
					width:150
				},
				{
					header : "四级代理-日期",
					dataIndex : "forthProxyDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "四级代理-批注",
					dataIndex : "forthProxyAnnotation",
					width:150
				},
				{
					header : "五级代理-日期",
					dataIndex : "fifthProxyDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "五级代理-批注",
					dataIndex : "fifthProxyAnnotation",
					width:150
				},
				{
					header : "六级代理-日期",
					dataIndex : "sixthProxyDate",
					width:100,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "六级代理-批注",
					dataIndex : "sixthProxyAnnotation",
					width:150
				},
				{
					header : "质检报告详情",
					dataIndex : "inspectionToolTip",
					width:80,
					align:'center',
					renderer: function(value,metaData,record,colIndex,store,view) 
					{
						//构造质检报告数据
						var inspectionData = new Array();
						var inspectionJson = Ext.decode(record.data.inspectionAllInfo);
						Ext.each(inspectionJson, function(inspection){
							var inspectionDataItem = new Array();
							//inspectionDataItem.push(inspection.id);
							//inspectionDataItem.push(inspection.ledgerQualificationId);
							inspectionDataItem.push(inspection.inspectionType);
							inspectionDataItem.push(inspection.inspectionDate);
							inspectionDataItem.push(inspection.inspectionContent);
							inspectionData.push(inspectionDataItem);
						});
						var html = "无质检信息";
						if(inspectionData.length > 0) {
							html = "<table>";
							html = html + "<thead><tr>" +
							"<td style='border:solid 1px;'>质检类型</td>" +
							"<td style='border:solid 1px;'>质检有效期</td>" +
							"<td style='border:solid 1px;'>质检内容</td>" +
							"</tr></thead><tbody>";
							Ext.each(inspectionData, function(item) {
								html = html + "<tr>";
								Ext.each(item, function(value) {
									if(value == null || value == 'undefined' || value == 'null')
										value = "";
									html = html + "<td style='border:solid 1px;'>" + value + "</td>";
								});
								html = html + "</tr>";
							});
							html = html + "</tbody></table>";
						}
						metaData.tdAttr = 'data-qtip="'+html+'"';
						return "<img src='" + _appctx + '/images/inspection.png' + "'/>"; 
					}
				},
				{
					header : "报关单",
					dataIndex : "declaration",
					width:50
				},
				{
					header : "问题汇总描述",
					dataIndex : "descriptionProblem",
					width:200
				},
				{
					header : "信息维护日期",
					dataIndex : "informationMaintenanceDate",
					width:80,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "信息维护人姓名",
					dataIndex : "informationMaintenanceName",
					width:90
				},
				{
					header : "审核状态",
					dataIndex : "approvalStatus",
					width:70
				},
				{
					header : "复审日期",
					dataIndex : "reviewDate",
					xtype: 'datecolumn',
					format:'Y-m-d H:i:s',
					width:70,
      				renderer:Ext.util.Format.dateRenderer('Y-m-d'),
				},
				{
					header : "复审人姓名",
					dataIndex : "reviewName",
					width:70
				},
				{
					header : "备注",
					dataIndex : "mark",
					width:200
				},
				{
					header : "是否有效",
					dataIndex : "valid",
					width:60,
					renderer:function(value) {
						if(value) {
							return '有效';
						} else {
							return '无效';
						}
					}
				},
				{
					text:"审核操作",
					xtype:'actioncolumn',
					sortable: true,
					width:60,
					items:[
						{
							text:'审核通过',
							xtype : 'button',
							tooltip: '审核通过',
							icon:_appctx+'/images/pass.gif',
							handler:function(grid, rowIndex, colIndex){

								//设置审核通过
								grid.getStore().getAt(rowIndex).set("approvalStatus","审核通过");
								//设置审核人
								grid.getStore().getAt(rowIndex).set("reviewName",username);
								//设置审核日期
								var curDate = new Date();
								var time = Ext.Date.format(curDate, 'Y-m-d H:i:s'); 
								grid.getStore().getAt(rowIndex).set("reviewDate",time);
								
								
								//当前行已被选中，这里需要再次选中，以显示在复选框中
								//取消选中所有的行
								var sm = grid.getSelectionModel();//获取选择模型
								sm.deselect(sm.getSelection());
								//选中当前行
								sm.select(rowIndex);
								
								//向后台发送ajax请求,更新审核信息
								
								var approvalArray = new Array();//创建数组，存储所有审核信息
								var approvalJSONStr = "";//存储一条审核信息的json字符串
								var start = "[";
								var end = "]";
								
								for (var i = 0; i < sm.getSelection().length; i++) {
									var id = sm.getSelection()[i].get("id");
									var approvalStatus = sm.getSelection()[i].get("approvalStatus");
									var reviewName = sm.getSelection()[i].get("reviewName");
									var reviewDate = sm.getSelection()[i].get("reviewDate");
									approvalJSONStr = "{id:'"+id+"',approvalStatus:'"+approvalStatus+"',reviewName:'"+reviewName+"',reviewDate:'"+reviewDate+"'}";
									approvalArray.push(approvalJSONStr);
								}
								
								Ext.Ajax.request({

								    url : baseUrl + "/ledgerQualification/updateLedgerQualificationOfReviewByPrimaryKey.json",
								
								    params: {approvalAllInfoJSONStr : start+approvalArray+end}
								
								});

							}
						},
						{
							text:'审核未通过',
							xtype : 'button',
							tooltip: '审核未通过',
							icon:_appctx+'/images/nopass.png',
							handler:function(grid, rowIndex, colIndex){

								//设置审核通过
								grid.getStore().getAt(rowIndex).set("approvalStatus","审核未通过");
								//设置审核人
								grid.getStore().getAt(rowIndex).set("reviewName",username);
								//设置审核日期
								var curDate = new Date();
								var time = Ext.Date.format(curDate, 'Y-m-d H:i:s'); 
								
								grid.getStore().getAt(rowIndex).set("reviewDate",time);
								
								
								//当前行已被选中，这里需要再次选中，以显示在复选框中
								//取消选中所有的行
								var sm = grid.getSelectionModel();//获取选择模型
								sm.deselect(sm.getSelection());
								//选中当前行
								sm.select(rowIndex);
								
								//向后台发送ajax请求,更新审核信息
								
								var approvalArray = new Array();//创建数组，存储所有审核信息
								var approvalJSONStr = "";//存储一条审核信息的json字符串
								var start = "[";
								var end = "]";
								
								for (var i = 0; i < sm.getSelection().length; i++) {
									var id = sm.getSelection()[i].get("id");
									var approvalStatus = sm.getSelection()[i].get("approvalStatus");
									var reviewName = sm.getSelection()[i].get("reviewName");
									var reviewDate = sm.getSelection()[i].get("reviewDate");
									approvalJSONStr = "{id:'"+id+"',approvalStatus:'"+approvalStatus+"',reviewName:'"+reviewName+"',reviewDate:'"+reviewDate+"'}";
									approvalArray.push(approvalJSONStr);
								}
								
								Ext.Ajax.request({

								    url : baseUrl + "/ledgerQualification/updateLedgerQualificationOfReviewByPrimaryKey.json",
								
								    params: {approvalAllInfoJSONStr : start+approvalArray+end}
								
								});
								
							}
						}
					]
				}
		];
		
		
		
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"LedgerQualificationTbar",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 7,
				items:[
				{
					text:'查找',
					width:80,
					pressed: true,
					hidden:true,
					ctCls: 'x-btn-over',
					handler:function(){
					
					//获取查询框的输入值
					//var supplierCode = Ext.getCmp("qualification_supplier_search_code").getValue();
					//设置store全局参数，每次store.load()都会带有该参数
					//var new_params = {
					//	supplierCode: supplierCode
					//};
			        //Ext.apply(pointsStore.getProxy().extraParams, new_params);
					
			        //store跳转第一页
			        pointsStore.currentPage = 1;
			        
			     	//store重新加载
			        pointsStore.load();
			        
			        
					
					}
				},{
					text:'重置',
					width:80,
					hidden:true,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						//Ext.getCmp("").setValue(null);	
					}
				},{
					text:'新建',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						Ext.create("ShopinDesktop.AddLedgerQualificationWindow", {
							id: "addLedgerQualificationWindow",
							baseUrl: baseUrl,
							caller: thisView
						}).show();
					}
				},
				//批量审核通过
				{
		            text: '审核通过',
		            width:80,
					pressed: true,
					ctCls: 'x-btn-over',
		            handler : function() {
					 
						//批量审核通过
					 	var sm = ledgerQualificationViewGridPanel.getSelectionModel();//获取选择模型
						var selected = sm.selected;
						if(selected.getCount() == 0) {
							Ext.Msg.alert("提示", "请选择至少一条记录！");
						}else{
							Ext.MessageBox.confirm('确认', '你确定要进行此操作吗？',function (e){
								if(e=='yes') {
									//批量审核通过
									for (var i = 0; i < sm.getSelection().length; i++) {
										sm.getSelection()[i].set("approvalStatus","审核通过");
										//设置审核人
										sm.getSelection()[i].set("reviewName",username);
										//设置审核日期
										var curDate = new Date()
										var time = Ext.Date.format(curDate, 'Y-m-d H:i:s'); 
										sm.getSelection()[i].set("reviewDate",time);
									}
									//当前行已被选中，这里需要再次选中，以显示在复选框中
									sm.select(sm.getSelection());
									
									//向后台发送ajax请求,更新审核信息
								
									var approvalArray = new Array();//创建数组，存储所有审核信息
									var approvalJSONStr = "";//存储一条审核信息的json字符串
									var start = "[";
									var end = "]";
									
									for (var i = 0; i < sm.getSelection().length; i++) {
										var id = sm.getSelection()[i].get("id");
										var approvalStatus = sm.getSelection()[i].get("approvalStatus");
										var reviewName = sm.getSelection()[i].get("reviewName");
										var reviewDate = sm.getSelection()[i].get("reviewDate");
										approvalJSONStr = "{id:'"+id+"',approvalStatus:'"+approvalStatus+"',reviewName:'"+reviewName+"',reviewDate:'"+reviewDate+"'}";
										approvalArray.push(approvalJSONStr);
									}
									
									Ext.Ajax.request({
	
									    url : baseUrl + "/ledgerQualification/updateLedgerQualificationOfReviewByPrimaryKey.json",
									
									    params: {approvalAllInfoJSONStr : start+approvalArray+end}
									
									});
									
									
								}
				             });
							
						}
						
		            }
		        },
		        //批量审核未通过
				{
		            text: '审核未通过',
		            width:80,
					pressed: true,
					ctCls: 'x-btn-over',
		            handler : function() {
					 
						//批量审核未通过
					 	var sm = ledgerQualificationViewGridPanel.getSelectionModel();//获取选择模型
						var selected = sm.selected;
						if(selected.getCount() == 0) {
							Ext.Msg.alert("提示", "请选择至少一条记录！");
						}else{
							Ext.MessageBox.confirm('确认', '你确定要进行此操作吗？',function (e){
								if(e=='yes') {
									//批量审核未通过
									for (var i = 0; i < sm.getSelection().length; i++) {
										sm.getSelection()[i].set("approvalStatus","审核未通过");
										//设置审核人
										sm.getSelection()[i].set("reviewName",username);
										//设置审核日期
										var curDate = new Date()
										var time = Ext.Date.format(curDate, 'Y-m-d H:i:s'); 
										sm.getSelection()[i].set("reviewDate",time);
									}
									//当前行已被选中，这里需要再次选中，以显示在复选框中
									sm.select(sm.getSelection());
									
									//向后台发送ajax请求,更新审核信息
								
									var approvalArray = new Array();//创建数组，存储所有审核信息
									var approvalJSONStr = "";//存储一条审核信息的json字符串
									var start = "[";
									var end = "]";
									
									for (var i = 0; i < sm.getSelection().length; i++) {
										var id = sm.getSelection()[i].get("id");
										var approvalStatus = sm.getSelection()[i].get("approvalStatus");
										var reviewName = sm.getSelection()[i].get("reviewName");
										var reviewDate = sm.getSelection()[i].get("reviewDate");
										approvalJSONStr = "{id:'"+id+"',approvalStatus:'"+approvalStatus+"',reviewName:'"+reviewName+"',reviewDate:'"+reviewDate+"'}";
										approvalArray.push(approvalJSONStr);
									}
									
									Ext.Ajax.request({
	
									    url : baseUrl + "/ledgerQualification/updateLedgerQualificationOfReviewByPrimaryKey.json",
									
									    params: {approvalAllInfoJSONStr : start+approvalArray+end}
									
									});
									
									
								}
				             });
							
						}
						
		            }
		        },
		        {//TODO
		        	text:'导入',
		        	width:80,
		        	pressed:true,
		        	ctCls: 'x-btn-over',
		        	handler:function(){
		        		win_upload.show();
		        	}
		        }
				]
			}]
		});
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.userInforStore,
			displayInfo : true
		});	
		
		
		//added by syk--start
		var form = new Ext.form.FormPanel({
		     baseCls : 'x-plain',
		     labelWidth : 70,
		     labelHeight: 150,
		     fileUpload : true,
		     defaultType : 'textfield',
		     items : [{
		        xtype : 'textfield', 
		        fieldLabel : '选择文件',
		        name : 'ledgerQualificationExcelUpload',
		        id : 'ledgerQualificationExcelUpload',
		        inputType : 'file',
		        anchor : '95%'
		       }]  
		});
		var win_upload = new Ext.Window({
		    title : '上传资质台帐',  
		    width :450,
		    height : 180,
		    modal : true,
		    x : 450,  
		    y : 100,  
		    layout : 'form',  
		    autoScroll : true,  
		    constrain : true,  
		    bodyStyle : 'padding:10px 10px 10px 10px;',  
		    items:form,
		    buttons : [{
		       text : '确认上传',
		       handler : function() {
		       if (form.form.isValid()) {
		        if(Ext.getCmp('ledgerQualificationExcelUpload').getValue() == ''){  
		         Ext.Msg.alert('温馨提示','请选择要上传的文件');  
		         return;  
		        } 
		        Ext.MessageBox.wait('正在读取Excel文件...','请等待');
		        form.getForm().submit({
		           url : baseUrl + "/ledgerQualification/importLedgerQualification",
		           method : 'POST',
		           success : function(form, action) {//action.result
		        	   win_upload.hide();
		        	   if(action.result.success == "true"){
							Ext.Msg.alert('提示',"恭喜！文件上传成功！");
						}else if(action.result.success == "false"){
							if(action.result.errorCode == "10000"){
								Ext.Msg.alert('错误',action.result.memo);
							}else{
								Ext.Msg.alert('错误',"导入失败！");
							}
						}
		           },  
		           failure : function(form, action) {
		               Ext.Msg.alert('错误',"文件上传失败，请重新操作！");  
		         }  
		        })  
		       }  
		      }  
		    }, {  
		        text : '取消',  
		        handler : function() {  
		            win_upload.hide();  
		    }  
		    }],  
		    closable: false,  
		    draggable: false,  
		    resizable: false  
		});
		//added by syk--end
		
		function delLedgerinfo(id){
			Ext.Ajax.request({
				url : "",
				method:'POST',
				params: { 
					id:id
				},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功');
						thisView.userInforStore.reload();
					}else{
						Ext.Msg.alert('错误','删除失败');
					}
				},
				failure: function(){
					Ext.Msg.alert('','删除失败，请与管理员联系');
				}
				})
		 }
	},
	listeners:{
		itemdblclick:function(view, record,item){
			var thisView = this;
			var baseUrl = this.baseUrl;
			if(record !=null){
				Ext.create("ShopinDesktop.UpdateLedgerQualificationWindow", {
					id: "updateLedgerQualificationWindow",
					baseUrl: baseUrl,
					caller: thisView,
					globalRecord: record
				}).show();
			}else{
				Ext.MessageBox.alert("提示","请选择一条记录");
			}
		}
	}

	
});