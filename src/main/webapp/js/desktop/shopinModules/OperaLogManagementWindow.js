/**
* 导购手动变价权限日志报表
* for demand
* feature https://tower.im/s/beCeH
* author qutengfei
*/

Ext.define('ShopinDesktop.OperaLogManagementWindow', {
	extend : "Ext.panel.Panel",
	requires : [ 'Ext.grid.Panel', 'Ext.data.Model', 'Ext.data.Store' ],
	baseUrl : _appctx,
	id : 'operaLogManagementWindow',
	constructor : function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines : true,
			rowLines : true,
			title : "操作日志查询",
			viewConfig : {
				//启用文字选择
			enableTextSelection : true
		},
		layout : "border",
		items : [ Ext.create("Ext.panel.Panel", {
			border : false,
			region : "north",
			height : "100%",
			layout : 'anchor',
			items : [ me.gridPanel ]
		}) ]
		});
	},
	initComponents : function(me) {
		var thisView = this;
		var baseUrl = this.baseUrl;

		this.sm = Ext.create('Ext.selection.CheckboxModel', {
			singleSelect : true
		});
		var checkbox = this.sm;

		this.seleteGuideInforStore = Ext.create("Ext.data.Store", {
			autoLoad : true,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [ "guideNo","operatTime","operator","description","type","typeDesc","operatorId","startTime","endTime","ifopen" ],
			proxy : {
				type : "ajax",
				url : _appctx + '/operaLog/getLogForGuideOperate.json',
				getMethod : function() {
					return 'POST';
				},
				reader : {
					type : "json",
					root : "obj.list",
					totalProperty : "total"
				},
				listeners : {
					exception : function(proxy, response, operation, eOpts) {
						if (response.status != 200) {
							Ext.MessageBox.alert("Error", "加载列表失败！");
						}
					}
				}
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					Ext.apply(store.proxy.extraParams, {
						guideNoId : Ext.getCmp('guideNoId').getValue(),
						typeId : Ext.getCmp('typeId').getValue(),
						operatorIdId : Ext.getCmp('operatorIdId').getValue(),
						operatorId : Ext.getCmp('operatorId').getValue(),
						operatTimeBId : Ext.getCmp('operatTimeBId').getValue(),
						operatTimeEId : Ext.getCmp('operatTimeEId').getValue()
					});
				},
				load : function(store, records, success, eOpts) {
				}
			}
		});
				
						this.guidesupplycolumns = [
						new Ext.grid.RowNumberer(),
					 	{
							dataIndex : 'sid',
							hidden : true,
							hideable : false
						}, 
						{header:'操作人ID',dataIndex:'operatorId',width:100,sortable:true},
						{header:'操作人姓名',dataIndex:'operator',width:100,sortable:true},
						{header:'导购编号',dataIndex:'guideNo',width:100,sortable:true},
						{header:'操作类型',dataIndex:'typeDesc',width:142,sortable:true},
//						{header:'操作类型',dataIndex:'type',width:120,sortable:true,
//							renderer:function(value){
//								if(value == 2) {
//									return '修改基本信息';
//								}
//								if(value == 1) {
//									return '修改导购变价权限';
//								}
//							}
//						},
						{header:'权限是否开通',dataIndex:'ifopen',width:80,sortable:true,
							renderer:function(value){
								if(value == 0) {
									return '否';
								}
								if(value == 1) {
									return '是';
								}
							}
						},
						{header:'操作时间',dataIndex:'operatTime',width:140,sortable:true},
						{header:'操作开始时间',dataIndex:'startTime',width:150,sortable:true},
						{header:'操作结束时间',dataIndex:'endTime',width:150,sortable:true},
						
						
					]
					
				var operaLogCombo= new Ext.data.ArrayStore({
					fields: ['operaLogCode','value'],
					data : [
						['-1',"全部"],['1',"修改导购变价权限"],['2',"修改基本信息"]
					]
				});
				
				var  infoStroe= this.seleteGuideInforStore;
				//页面展示模块
				this.gridPanel = Ext.create('Ext.grid.Panel', {
			      	id : 'operationLogID',
		      		border:false,
		      		enableTabScroll : true,
					autoScroll : true,
					anchor: "100% 100%",
					loadMask:{msg : '数据加载中...'},
					bbar : {
						xtype : 'pagingtoolbar',
						store : infoStroe,
						displayInfo : true
					},
					columns :this.guidesupplycolumns,
					store: this.seleteGuideInforStore,
					tbar :[
						{
						xtype: "buttongroup",
						width:"100%",
						columns: 4,
						items:[
							{
								xtype : "textfield",
								id:"operatorIdId",
								fieldLabel:"操作人ID",
								labelAlign:"right",
								labelWidth: 80,
								width : 200,
							},{
								xtype : "textfield",
								id:"operatorId",
								fieldLabel:"操作人姓名",
								labelAlign:"right",
								labelWidth: 80,
								width : 200,
							},{
								xtype : "combo",
								id:"typeId",
								fieldLabel:"操作类型",
								store:operaLogCombo,
								valueField: 'operaLogCode',
								displayField:'value',
								labelAlign:"right",
								labelWidth: 80,
								width : 200,
							},{
								xtype : "textfield",
								id:"guideNoId",
								fieldLabel:"导购编号",
								labelAlign:"right",
								labelWidth: 80,
								width : 200,
							},{
								xtype: "datefield",
								id:"operatTimeBId",
								fieldLabel:"操作开始时间",
								labelAlign:"right",
								labelWidth: 80,
								width : 200,
								format: "Y-m-d",
							},{
								xtype: "datefield",
								id:"operatTimeEId",
								fieldLabel:"操作结束时间",
								labelAlign:"right",
								labelWidth: 80,
								width : 200,
								format: "Y-m-d",
							},{
								xtype: "button",
								icon:baseUrl + "/images/find.png",
								pressed: true,
								text: "查询",
								aligh : "right",
								width:80,
								margin:"0 10 0 50",
								handler: function() {
									infoStroe.load({
										params:{
											guideNoId : Ext.getCmp('guideNoId').getValue(),
											typeId : Ext.getCmp('typeId').getValue(),
											operatorIdId : Ext.getCmp('operatorIdId').getValue(),
											operatorId : Ext.getCmp('operatorId').getValue(),
											operatTimeId : Ext.getCmp('operatTimeBId').getValue(),
											operatTimeId : Ext.getCmp('operatTimeEId').getValue()
										}
									});
								}
							},
					]}],
			});
	Ext.getCmp('typeId').select(operaLogCombo.getAt(0));
	}

});
