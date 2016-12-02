/*
 * 合同台账信息
 * author liuchunlong
 */
Ext.define("ShopinDesktop.LedgerContractView",{
	extend:"Ext.grid.Panel",
	baseUrl: "",
	tbar:null,
	columns:null,
	pageBar:null,
	userInforStore:null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'ledgerContractView',
			columns: this.columns,
			tbar: this.tbar,
			bbar : this.pageBar,
			store: this.userInforStore,
			viewConfig : {
				enableTextSelection : true,
				getRowClass: function (record, rowIndex, rowParams, store) {
					if(record.data.valid) {   //有效
						if(record.data.parentId!=0 && record.data.cabinetStatus != '撤柜'){  //parentId = 0 为父，非0为子
							return "row-related";      //关联的子条款：蓝色
						  }
						if(record.data.cabinetStatus == '撤柜') {  
                			return "row-status";		//撤柜：红色
                		}
	                	}else {    //无效
	                		return "row-valid";     //无效：灰色
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
				   //{name:"",type:'date',dateFormat:'Y-m-d H:i:s'},	//使用范例
				   	{name:"id"},
				   	//added by syk
				   	{name:"clearType"},
				   	
				   	{name:"parentId"},
				   	
				   	{name:"cooperationWay"},
				   	
				   	{name:"bizContractVersion"},
				   	
				   	{name:"area"},
				   	
				   	{name:"decorateRules"},
				   	
				   	{name:"clearDeduction"},
				   	
				   	{name:"clearThreshold"},
				   	
				   	{name:"brandLevel"},
				   	
				   	{name:"courseAllInfo"},

					{name:"settlementStatus"},
				
					{name:"signMark"},
					
					{name:"cabinetStatus"},
					
					{name:"cabinetDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"authorizedStartDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"authorizedEndDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"supplierCode"},
				
					{name:"supplierName"},
				
					{name:"legalRepresentative"},
				
					{name:"attorney"},
				
					{name:"mobilePhone1"},
				
					{name:"mobilePhone1Name"},
				
					{name:"mobilePhone2"},
				
					{name:"mobilePhone2Name"},
				
					{name:"fixedTelephone"},
				
					{name:"fixedTelephoneName"},
					
					{name:"basicSigningDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"category"},
				
					{name:"brandName"},
				
					{name:"storeEncoding"},
				
					{name:"storeName"},
				
					{name:"deductionRate"},
				
					{name:"assessmentIndicator"},
				
					{name:"signingDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"startDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"endDate",type:'date',dateFormat:'Y-m-d H:i:s'},
				
					{name:"contractDeadline",type:'date',dateFormat:'Y-m-d H:i:s'},
					
					{name:"supplemental"},
				
					{name:"managementFees"},
				
					{name:"cardFees"},
				
					{name:"contractVersion"},
				
					//{name:"checkTag"},
				
					//{name:"checkName"},
				
					//{name:"checkDate",type:'date',dateFormat:'Y-m-d H:i:s'},
					
					{name:"valid"},
				
					{name:"mark"},
					],
 			proxy: {
				type: "ajax",
				url : baseUrl + "/ledgerContract/selectLedgerContractCustomList.json",
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

    	
		var pointsStore = this.userInforStore;
		this.columns=[
				new Ext.grid.RowNumberer({
							width:40
				}),
//				使用范例
//				{
//					header : "工作联系单截止日期",
//					dataIndex : "contactDeadline",
//					width:120,
//      			renderer:Ext.util.Format.dateRenderer('Y-m-d')
//				},
				{
					header : "ID",
					dataIndex : "id",
					hidden : true,
					width:50
				},
				{
					header : "parentId",
					dataIndex : "parentId",
					hidden : true,
					width:50
				},
				{
					header : "协议历程",
					dataIndex : "courseAllInfo",
					hidden : true,
					width:50
				},
				{
					header : "结算状态",
					dataIndex : "settlementStatus",
					width:70
				},
				{
					header : "签署标记",
					dataIndex : "signMark",
					width:200
				},
				
				{
					header : "在柜状态",
					dataIndex : "cabinetStatus",
					width:70
				},
				{
					header : "撤柜日期",
					dataIndex : "cabinetDate",
					width:70,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},
				
				{
					header : "授权开始日",
					dataIndex : "authorizedStartDate",
					width:70,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},
				{
					header : "授权结束日",
					dataIndex : "authorizedEndDate",
					width:70,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
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
					header : "法人代表",
					dataIndex : "legalRepresentative",
					width:60
				},
				{
					header : "委托代理人",
					dataIndex : "attorney",
					width:70
				},
				
//				{
//					header : "移动电话(一)",
//					dataIndex : "mobilePhone1",
//					width:130
//				},
//				{
//					header : "移动电话(一)姓名",
//					dataIndex : "mobilePhone1Name",
//					width:100
//				},
//				{
//					header : "移动电话(二)",
//					dataIndex : "mobilePhone2",
//					width:130
//				},
//				{
//					header : "移动电话(二)姓名",
//					dataIndex : "mobilePhone2Name",
//					width:100
//				},
//				{
//					header : "固定电话",
//					dataIndex : "fixedTelephone",
//					width:130
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
					header : "基本签订日期",
					dataIndex : "basicSigningDate",
					width:80,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
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
					header : "品牌级别",
					dataIndex : "brandLevel",
					width:150
				},
				{
					header : "门店编码",
					dataIndex : "storeEncoding",
					width:60
				},
				{
					header : "门店",
					dataIndex : "storeName",
					width:70
				},
				{
					header : "扣率",
					dataIndex : "deductionRate",
					width:50,
					renderer: function(value,metaData,record,colIndex,store,view){
						var str = value+"";
						if (str.indexOf('.')>=0){
							return str+"%";
						}else{
							return str+".0%";
						}
						
					}
				},
				{
					header : "考核指标",
					dataIndex : "assessmentIndicator",
					width:200
				},{//added by syk
					header : "清算类型",
					dataIndex : "clearType",
					width:200
				},{
					header : "清算阈值",
					dataIndex : "clearThreshold",
					width:200
				},{
					header : "清算扣率",
					dataIndex : "clearDeduction",
					width:200
				},{
					header : "商户装修手册",
					dataIndex : "decorateRules",
					width:80,
					align:'center',
					renderer:function(value) {
						if(value) {
							return '有';
						} else {
							return '无';
						}
					}
				},{
					header : "面积",
					dataIndex : "area",
					width:80
				},{
					header : "商务合同版本",
					dataIndex : "bizContractVersion",
					width:200
				},//added end
				{
					header : "商务签订日期",
					dataIndex : "signingDate",
					width:80,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},
				{
					header : "开始日期",
					dataIndex : "startDate",
					width:80,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},
				{
					header : "结束日期",
					dataIndex : "endDate",
					width:80,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},
				{
					header : "合同截止日期",
					dataIndex : "contractDeadline",
					width:80,
					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},
				{
					header : "补充协议",
					dataIndex : "supplemental",
					width:200
				},
				{
					header : "综合管理费(/元/天/平米)",
					dataIndex : "managementFees",
					width:150
				},
				{
					header : "内外卡手续费",
					dataIndex : "cardFees",
					width:120
				},
				{
					header : "合同版本",
					dataIndex : "contractVersion",
					width:120
				},
				{
					header : "合同详情",
					dataIndex : "contractDetailsToolTip",
					width:60,
					align:'center',
					renderer: function(value,metaData,record,colIndex,store,view) {
						//构造协议历程数据
						var courseData = new Array();
						var courseJson = Ext.decode(record.data.courseAllInfo);
						Ext.each(courseJson, function(course){
							var courseDataItem = new Array();
							
							//courseDataItem.push(course.id);
							//courseDataItem.push(course.ledgerContractId);
							courseDataItem.push(course.contractReturnDate);
							courseDataItem.push(course.contractType);
							courseDataItem.push(course.contractBeginDate);
							courseDataItem.push(course.contractEndDate);
							courseDataItem.push(course.contractReviewStatus);
							courseDataItem.push(course.contractReviewer);
							courseDataItem.push(course.contractReviewDate);
							
							courseData.push(courseDataItem);
						});
						
						var html = "无合同信息";
						if(courseData.length > 0) {
							html = "<table><thead>" +
							"<tr>" +
							"<td style='border:solid 1px;'>合同返回日期</td>" +
							"<td style='border:solid 1px;'>合同类型</td>" +
							"<td style='border:solid 1px;'>合同有效期</td>" +
							"<td style='border:solid 1px;'>审核状态</td>" +
							"</tr>" +
							"</thead><tbody>";
							Ext.each(courseData, function(item) {
								if(item[0] == null || item[0] == 'undefined' || item[0] == 'null')
									item[0] = "";
								if(item[1] == null || item[1] == 'undefined' || item[1] == 'null')
									item[1] = "";
								if(item[2] == null || item[2] == 'undefined' || item[2] == 'null')
									item[2] = "";
								if(item[3] == null || item[3] == 'undefined' || item[3] == 'null')
									item[3] = "";
								if(item[4] == null || item[4] == 'undefined' || item[4] == 'null')
									item[4] = "";
								html = html + "<tr>" +
								"<td style='border:solid 1px;'>" + item[0] + "</td>" +
								"<td style='border:solid 1px;'>" + item[1] + "</td>" +
								"<td style='border:solid 1px;'>" + item[2] + "至" + item[3] + "</td>" +
								"<td style='border:solid 1px;'>" + item[4] + "</td>" +
								"</tr>";
							});
						}
						html = html + "</tbody></table>";
						metaData.tdAttr = 'data-qtip="'+html+'"';
						return "<img src='" + _appctx + '/images/inspection.png' + "'/>"; 
					}
				},
				
//				{
//					header : "审核标记",
//					dataIndex : "checkTag",
//					width:70
//				},
//				{
//					header : "审核人",
//					dataIndex : "checkName",
//					width:60
//				},
//				{
//					header : "审核日期",
//					dataIndex : "checkDate",
//					width:70,
//					renderer:Ext.util.Format.dateRenderer('Y-m-d')
//				},
				{
					header : "合作方式",
					dataIndex : "cooperationWay",
					width:200
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
				{//TODO 添加补充条款
					header:"添加补充条款",
					dataIndex:"parentId",
					width:90,
					align:'center',
					renderer:function(value){
						if(value!=null && value!=0){
							return "";
						}else{
							return "<img src='" + _appctx + '/images/add.gif' + "'/>";
						}
					},
					listeners:{
						click:function(value,metaData,rowIndex,colIndex,store,view) {
						    var record = pointsStore.getAt(rowIndex);   //Get the Record
						    var parentId = record.get("parentId");
						    if(record !=null && parentId!=0){
//								Ext.create("ShopinDesktop.SupplementOfLedgerContractWindow", {
//									id: "supplementOfLedgerContractWindow",
//									baseUrl: baseUrl,
//									caller: thisView,
//									globalRecord: record
//								}).show();
							}else if(record ==null){
								Ext.MessageBox.alert("提示","请选择一条记录");
							}else{
								Ext.create("ShopinDesktop.SupplementOfLedgerContractWindow", {
									id: "supplementOfLedgerContractWindow",
									baseUrl: baseUrl,
									caller: thisView,
									globalRecord: record
								}).show();
							}
						}
					}
				}
				
				
		];
		
		//绘制搜索界面
		
		
		
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"LedgerContractTbar",
			
			items:[
					
								{
									xtype: "buttongroup",
									width:"100%",
									columns: 7,
									items:[
										{
											text:'查找',
											width:80,
											hidden:true,
											pressed: true,
											ctCls: 'x-btn-over',
											handler:function(){
											
									        //store跳转第一页
									        pointsStore.currentPage = 1;
									        
									     	//store重新加载
									        pointsStore.load();
									        
											}
										},
										{
											text:'重置',
											width:80,
											hidden:true,
											pressed: true,
											ctCls: 'x-btn-over',
											handler:function(){
												//Ext.getCmp("").setValue(null);	
											}
										},
										{
											text:'新建',
											width:80,
											pressed: true,
											ctCls: 'x-btn-over',
											handler:function(){
												Ext.create("ShopinDesktop.AddLedgerContractWindow", {
													id: "addLedgerContractWindow",
													baseUrl: baseUrl,
													caller: thisView
												}).show();
											}
										},
								        { 
								        	text:'导入',
								        	width:80,
								        	pressed:true,
								        	ctCls: 'x-btn-over',
								        	handler:function(){
									       		win_upload.show();
									       	}
								        }
									]
								}
				]

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
		        name : 'ledgerContractExcelUpload',
		        id : 'ledgerContractExcelUpload',
		        inputType : 'file',
		        anchor : '95%'
		       }]  
		});
		var win_upload = new Ext.Window({
		    title : '上传合同台帐',  
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
		        if(Ext.getCmp('ledgerContractExcelUpload').getValue() == ''){  
		         Ext.Msg.alert('温馨提示','请选择要上传的文件');  
		         return;  
		        } 
		        Ext.MessageBox.wait('正在读取Excel文件...','请等待');
		        form.getForm().submit({
		           url : baseUrl + "/ledgerContract/importLedgerContract",
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
		//FIXME 因为分页问题，去掉
//		this.pageBar = Ext.create("Ext.toolbar.Paging", {
//			store : this.userInforStore,
//			displayInfo : true
//		});	
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
				Ext.create("ShopinDesktop.UpdateLedgerContractWindow", {
					id: "updateLedgerContractWindow",
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
