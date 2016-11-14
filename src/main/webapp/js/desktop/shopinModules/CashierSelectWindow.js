/**
 * 收银员流水查询
 * @param {Object} config
 * @memberOf {TypeName}
 */
Ext.define('ShopinDesktop.CashierSelectWindow', {
	extend:"Ext.panel.Panel",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	id : 'cashierSelectWindow',
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title: "旺pos流水统计查询",
			viewConfig: {
				//启用文字选择
				enableTextSelection: true
			},
			layout: "border",
			items: [
				Ext.create("Ext.panel.Panel", {
					border: false,
					region:"north",
					height: "100%",
					layout : 'anchor',
					items: [
						me.gridPanel
					]
				})
			]
		});
	},
	initComponents: function(me) {
		var thisView = this;
		var baseUrl = this.baseUrl;

		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;

		this.seleteCashierRepStore = Ext.create("Ext.data.Store", {
			autoLoad: false,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
			          	"shopSid","shopName","payTime","supplySid","brandName","brandSid","paymentTypeSid","cashierNumber","saleSum",
			          	"deviceEn","bankNo","refNo","terminalNo","cashierNo","startTime","endTime","saleAllPrice","guideNo"
					],
			proxy: {
				type: "ajax",
				url : _appctx + '/oms/selectCashierList.json',
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
			},
			listeners: {
				beforeload: function(store, operation, eOpts) {
					var shopSid = Ext.getCmp('shopStatisticsSid').getValue();
					var startTime = Ext.getCmp('startTime').getValue();
					var endTime = Ext.getCmp('endTime').getValue();
					if(shopSid==null||shopSid==""||startTime==null||startTime==""||endTime==""||endTime==null){
						Ext.Msg.alert('提示', '门店,日期必选!');
						return ;
					}
					
					Ext.apply(store.proxy.extraParams, {
						shopSid : Ext.getCmp('shopStatisticsSid').getValue(),
						guideNo : Ext.getCmp('guideNoId').getValue(),
						startTime :  Ext.util.Format.date(Ext.getCmp('startTime').getValue(),'Y-m-d'),
						endTime : Ext.util.Format.date(Ext.getCmp('endTime').getValue(),'Y-m-d'),
						supplySid : Ext.getCmp('supplySidId').getValue(),
						cashierNumber : Ext.getCmp('cashierNumberId').getValue(),
						terminalNo : Ext.getCmp('terminalId').getValue()
				        });
				},
				load: function(store, records, success, eOpts) {
				}
			}
		});

		var shopStore = new Ext.data.JsonStore({
			autoLoad : true,
			proxy : {
				type : 'ajax',
				api : {
					read : _appctx + '/guideSupply/getShopsList'
				},
				idParam : 'sid',
				reader : {
					type : 'json',
					root : 'result'
				}
			},
			fields: ["sid", "shopName"]
			
		});
		
		this.guideinfocolumns = [
				new Ext.grid.RowNumberer(),
			 	{
					dataIndex : 'sid',
					hidden : true,
					hideable : false
				}, 
				{header:'门店号',dataIndex:'shopSid',align:'center',hidden : true,hideable : false},
				{header:'门店名称',dataIndex:'shopName',align:'center',width:80,sortable:true},
				{header:'日期',dataIndex:'payTime',width:160,align:'center',sortable:true},
				{header:'供应商码',dataIndex:'supplySid',align:'center',width:80,sortable:true,editor:new Ext.form.TextField()},
				{header:'品牌名称',dataIndex:'brandName',align:'center',width:110,sortable:true},
				{header:'流水号',dataIndex:'cashierNumber',align:'center',width:110,sortable:true,editor:new Ext.form.TextField()},
				{header:'数量',dataIndex:'saleSum',width:40,align:'center',sortable:true},
				{header:'金额',dataIndex:'saleAllPrice',align:'center',width:60,sortable:true},
				{header:'收银员登陆号',dataIndex:'guideNo',align:'center',width:140,sortable:true,editor:new Ext.form.TextField()},
				/*{header:'支付方式',dataIndex:'paymentTypeSid',align:'center',width:70,sortable:true,
					renderer:function(value){
						if(value == 44) {
							return '旺pos支付';
						}
					}
				},*/
				{header:'设备EN号',dataIndex:'deviceEn',align:'center',width:140,sortable:true},
				{header:'银行卡号',dataIndex:'bankNo',align:'center',width:140,sortable:true},
				{header:'参考号',dataIndex:'refNo',align:'center',width:140,sortable:true},
				{header:'银行终端号',dataIndex:'terminalNo',align:'center',width:140,sortable:true}
				
		]
		
		/*var validPayCombo= new Ext.data.ArrayStore({
			fields: ['validPayCode','value'],
			data : [
			['',"全部"],['44',"旺post支付"]
			]
		});*/
		
/*		var guideStatusCombo= new Ext.data.ArrayStore({
			fields: ['guideStatusCode','value'],
			data : [
				['-1',"全部"],['0',"初始状态"],['1',"在柜"],['2',"不在柜"]
			]
		});*/
		
		var  infoStroe= this.seleteCashierRepStore;

		this.gridPanel = Ext.create('Ext.grid.Panel', {
			plugins:[
	                 Ext.create('Ext.grid.plugin.CellEditing',{
	                     clicksToEdit:1 //设置单击单元格编辑
	                 })
		      		],
	      	id : 'cashierRepPanel',
      		border:false,
      		enableTabScroll : true,
			autoScroll : true,
			anchor: "100% 100%",
			loadMask:{msg : '数据加载中...'},
			bbar : {
				xtype : 'pagingtoolbar',
				store : this.seleteCashierRepStore,
				displayInfo : true
			},
			columns :this.guideinfocolumns,
			columnLines:true,
			store: this.seleteCashierRepStore,
//			selModel: this.sm,
			tbar :[
				{
				xtype: "buttongroup",
				width:"100%",
				columns: 7,
				items:[
					 {
						xtype:"combo",
						fieldLabel : '门店',
						labelWidth: 30,
						labelAlign:"right",
						width : 120,
						id:"shopStatisticsSid",
						store : shopStore,
						valueField: 'sid',
						displayField:'shopName',
						hiddenName:'sid',
						triggerAction : 'all',
						name:'shop',
						mode:'local'
					},
					{
						xtype : "textfield",
						id:"cashierNumberId",
						fieldLabel:"流水号",
						labelAlign:"right",
						labelWidth: 60,
						width : 120,
					},
					{
						xtype : "textfield",
						id:"supplySidId",
						fieldLabel:"供应商码",
						labelAlign:"right",
						labelWidth: 60,
						width : 120,
					},{
						xtype : "textfield",
						id:"terminalId",
						fieldLabel:"终端号",
						labelAlign:"right",
						labelWidth: 60,
						width : 120,
					},{
						xtype : "textfield",
						id:"guideNoId",
						fieldLabel:"导购号",
						labelAlign:"right",
						labelWidth: 60,
						width : 120,
					},
					{
						id: "startTime",
						fieldLabel:"开始时间",
						labelAlign: "right",
						xtype: "datefield",
						labelWidth: 60,
						labelAlign:"right",
						format: "Y-m-d",
						allowBlank:false,
						width:190
					},
					{
						id: "endTime",
						fieldLabel:"结束时间",
						labelAlign: "right",
						xtype: "datefield",
						labelWidth: 60,
						labelAlign:"right",
						format: "Y-m-d",
						allowBlank:false,
						width:190
					},{
						xtype: "button",
						icon:baseUrl + "/images/find.png",
						pressed: true,
						text: "查询",
						aligh : "right",
						width:50,
						margin:"0 10 0 10",
						handler: function() {
							var shopSid = Ext.getCmp('shopStatisticsSid').getValue();
							var startTime = Ext.getCmp('startTime').getValue();
							var endTime = Ext.getCmp('endTime').getValue();
							if(shopSid==null||shopSid==""||startTime==null||startTime==""||endTime==""||endTime==null){
								Ext.Msg.alert('提示', '门店,日期必选!');
								return ;
							}
							
							infoStroe.load({
								params:{
									shopSid : Ext.getCmp('shopStatisticsSid').getValue(),
									guideNo : Ext.getCmp('guideNoId').getValue(),
									startTime :  Ext.util.Format.date(Ext.getCmp('startTime').getValue(),'Y-m-d'),
									endTime : Ext.util.Format.date(Ext.getCmp('endTime').getValue(),'Y-m-d'),
									supplySid : Ext.getCmp('supplySidId').getValue(),
									cashierNumber : Ext.getCmp('cashierNumberId').getValue(),
									terminalNo : Ext.getCmp('terminalId').getValue()
								}
							});
						}
					},
					{
						xtype : 'button',
						text : '导出EXCEL',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						margin : '0 0 0 0',
						handler : function() {
						 var shopSid = Ext.getCmp('shopStatisticsSid').getValue();
						 var guideNo = Ext.getCmp('guideNoId').getValue();
						 var startTime =  Ext.util.Format.date(Ext.getCmp('startTime').getValue(),'Y-m-d');
						 var endTime = Ext.util.Format.date(Ext.getCmp('endTime').getValue(),'Y-m-d');
						 var supplySid = Ext.getCmp('supplySidId').getValue();
						 var cashierNumber = Ext.getCmp('cashierNumberId').getValue();
						 var terminalNo = Ext.getCmp('terminalId').getValue();
						 
						 if(shopSid==null||shopSid==""||startTime==null||startTime==""||endTime==""||endTime==null){
								Ext.Msg.alert('提示', '门店,日期必选!');
								return ;
							}
							
							var appWindow = window.open(_appctx+"/oms/exportCashierReportToExcel.json?shopSid="+shopSid+
									"&guideNo="+guideNo+"&startTime="+startTime+"&endTime="+endTime
									+"&supplySid="+supplySid+"&cashierNumber="+cashierNumber+"&terminalNo="+terminalNo);
							appWindow.focus();
						}
					}
					]}
				],
			listeners:{
				/*itemdblclick:function(view, record,item){
					 Ext.create('ShopinDesktop.GuideinfoUpdateWindow',{
					  	record : record,
					  	type : 'detail'
					}).show();
				}*/
			}
		});
		
//		shopStore.on('load', function (){
//			 var shopSid = parseInt(shopid);
//     		 Ext.getCmp('shopStatisticsSid').setValue(shopSid);    
//		});
		
//		Ext.getCmp('payValidBit').select(validPayCombo.getAt(0));
//		Ext.getCmp('shopGuideStatusId').select(guideStatusCombo.getAt(0));
	}
});
