/**
 * 收银员长短款报表查询
 * @param {Object} config
 * @memberOf {TypeName}
 */
Ext.define('ShopinDesktop.LongShortItemWindow', {
	extend:"Ext.panel.Panel",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	id : 'longShortItemWindow',
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title: "长短款表报查询",
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

		this.seleteLongShortRepStore = Ext.create("Ext.data.Store", {
			autoLoad: false,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
			          	"shopSid","shopName","payTime","supplySid","brandName","brandSid","paymentTypeSid","cashierNumber","saleSum",
			          	"deviceEn","bankNo","refNo","terminalNo","cashierNo","startTime","endTime","saleAllPrice","guideNo"
					],
			proxy: {
				type: "ajax",
				url : _appctx + '/oms/selectLongShortList.json',
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
					var startTime = Ext.getCmp('startTimeId').getValue();
					var endTime = Ext.getCmp('endTimeId').getValue();
					if(startTime==null||startTime==""||endTime==null||endTime==""){
						Ext.Msg.alert('提示', '时间日期必选!');
						return ;
					}
					
					Ext.apply(store.proxy.extraParams, {
						guideNo : Ext.getCmp('guideNoSid').getValue(),
						startTime :  Ext.util.Format.date(Ext.getCmp('startTimeId').getValue(),'Y-m-d'),
						endTime : Ext.util.Format.date(Ext.getCmp('endTimeId').getValue(),'Y-m-d'),
						terminalNo : Ext.getCmp('terminalSid').getValue()
				        });
				},
				load: function(store, records, success, eOpts) {
				}
			}
		});

		this.guideinfocolumns = [
				new Ext.grid.RowNumberer(),
			 	{
					dataIndex : 'sid',
					hidden : true,
					hideable : false
				}, 
				{header:'银行终端号',dataIndex:'terminalNo',align:'center',width:140,sortable:true},
//				{header:'收银员登陆号',dataIndex:'guideNo',align:'center',width:140,sortable:true},
				{header:'金额',dataIndex:'saleAllPrice',align:'center',width:60,sortable:true},
				{header:'设备EN号',dataIndex:'deviceEn',align:'center',width:140,sortable:true}
				
		]
		
		var  infoStroe= this.seleteLongShortRepStore;

		this.gridPanel = Ext.create('Ext.grid.Panel', {
	      	id : 'longShortRepPanel',
      		border:false,
      		enableTabScroll : true,
			autoScroll : true,
			anchor: "100% 100%",
			loadMask:{msg : '数据加载中...'},
			bbar : {
				xtype : 'pagingtoolbar',
				store : this.seleteLongShortRepStore,
				displayInfo : true
			},
			columns :this.guideinfocolumns,
			columnLines:true,
			store: this.seleteLongShortRepStore,
//			selModel: this.sm,
			tbar :[
				{
				xtype: "buttongroup",
				width:"100%",
				columns: 7,
				items:[
					 {
						xtype : "textfield",
						id:"terminalSid",
						fieldLabel:"终端号",
						labelAlign:"right",
						labelWidth: 60,
						width : 120,
					},{
						xtype : "textfield",
						id:"guideNoSid",
						fieldLabel:"导购号",
						labelAlign:"right",
						labelWidth: 60,
						width : 120,
					},
					{
						id: "startTimeId",
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
						id: "endTimeId",
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
							var startTime = Ext.getCmp('startTimeId').getValue();
							var endTime = Ext.getCmp('endTimeId').getValue();
							if(startTime==null||startTime==""||endTime==null||endTime==""){
								Ext.Msg.alert('提示', '时间日期必选!');
								return ;
							}
							
							infoStroe.load({
								params:{
									guideNo : Ext.getCmp('guideNoSid').getValue(),
									startTime :  Ext.util.Format.date(Ext.getCmp('startTimeId').getValue(),'Y-m-d'),
									endTime : Ext.util.Format.date(Ext.getCmp('endTimeId').getValue(),'Y-m-d'),
									terminalNo : Ext.getCmp('terminalSid').getValue()
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
						 var guideNo = Ext.getCmp('guideNoSid').getValue();
						 var startTime =  Ext.util.Format.date(Ext.getCmp('startTimeId').getValue(),'Y-m-d');
						 var endTime = Ext.util.Format.date(Ext.getCmp('endTimeId').getValue(),'Y-m-d');
						 var terminalNo = Ext.getCmp('terminalSid').getValue();
						 
							if(startTime==null||startTime==""||endTime==null||endTime==""){
								Ext.Msg.alert('提示', '时间日期必选!');
								return ;
							}
							
							var appWindow = window.open(_appctx+"/oms/exportLongShortReportToExcel.json?guideNo="+guideNo+
									"&startTime="+startTime+"&endTime="+endTime+"&terminalNo="+terminalNo);
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
		
//		Ext.getCmp('payValidBit').select(validPayCombo.getAt(0));
//		Ext.getCmp('shopGuideStatusId').select(guideStatusCombo.getAt(0));
	}
});
