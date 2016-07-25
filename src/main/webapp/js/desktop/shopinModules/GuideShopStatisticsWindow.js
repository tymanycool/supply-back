/**
 * 按门店统计查询
 * @param {Object} config
 * @memberOf {TypeName}
 */
Ext.define('ShopinDesktop.GuideShopStatisticsWindow', {
	extend:"Ext.panel.Panel",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	id : 'guideShopStatisticsWindow',
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title: "按门店统计查询",
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

		this.seleteGuideInforStore = Ext.create("Ext.data.Store", {
			autoLoad: true,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
					"sid","guideNo","chestNum","name","age","stature","address","presentAddress","education","educationCartNum","kitasNum",
					"kitasEndtime","healthCartNum","healthCartEndtime","spell","sex","mobile","email","guideCard","guideBit","chestBit",
					"depositBit","depositNum","entrytime","leavetime","validBit","operator","createtime","guideStatus","shopName","chestcardNumber"
					],
			proxy: {
				type: "ajax",
				url : _appctx + '/guideinfo/getAllGuideInfoListByShop.json',
				getMethod: function(){
					return 'POST';
				},
				reader: {
					type: "json",
					root : "obj.list",
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
					Ext.apply(store.proxy.extraParams, {
				         	shopId : Ext.getCmp('shopStatisticsSid').getValue(),
				         	guideStatusId : Ext.getCmp('shopGuideStatusId').getValue(),
				         	healthCartEndtime : Ext.getCmp('shopHealthCartEndtime').getValue(),
							kitasEndtime : Ext.getCmp('shopKitasEndtime').getValue(),
							validBit : Ext.getCmp('shopValidBit').getValue()
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
				{header:'导购编号',dataIndex:'guideNo',hidden : true,hideable : false},
				{header:'姓名',dataIndex:'name',width:80,sortable:true},
				{header:'门店',dataIndex:'shopName',width:80,sortable:true},
				{header:'胸卡编号',dataIndex:'chestcardNumber',width:80,sortable:true},
				{header:'性别',dataIndex:'sex',width:40,sortable:true},
				{header:'手机号码',dataIndex:'mobile',width:90,sortable:true},
				{header:'身份证号',dataIndex:'guideCard',width:150,sortable:true},
				{header:'是否是导购',dataIndex:'guideBit',width:70,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '不是';
						}
						if(value == 1) {
							return '是';
						}
					}
				},
				{header:'导购状态',dataIndex:'guideStatus',width:60,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '初始状态';
						}
						if(value == 1) {
							return '在柜';
						}
						if(value == 2) {
							return '不在柜';
						}
					}
				},
				{header:'是否有效',dataIndex:'validBit',width:60,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '无效';
						}
						if(value == 1) {
							return '有效';
						}
					}
				},
				{header:'暂住证有效截止时间',dataIndex:'kitasEndtime',width:140,sortable:true},
				{header:'健康证有效截止时间',dataIndex:'healthCartEndtime',width:140,sortable:true},
				{header:'创建时间',dataIndex:'createtime',width:140,sortable:true},
				{
					text:"查看详细信息",
					xtype:'actioncolumn',
					sortable: true,
					width:100,
					align:'center',
					items :[
						{
						text : '查看详细信息',
						xtype : 'button',
						tooltip: '查看详细信息',
						icon:_appctx+'/images/edit.gif',
						handler:function(grid, rowIndex, colIndex){
							var record = grid.getStore().getAt(rowIndex); 
						
							 Ext.create('ShopinDesktop.GuideinfoUpdateWindow',{
							  	record : record,
							  	type : 'detail'
							}).show();
						}
					}
				]	
			}
		]
		
		var validBitCombo= new Ext.data.ArrayStore({
			fields: ['validBitCode','value'],
			data : [
			['-1',"全部"],['0',"无效"],['1',"有效"]
			]
		});
		
		var guideStatusCombo= new Ext.data.ArrayStore({
			fields: ['guideStatusCode','value'],
			data : [
				['-1',"全部"],['0',"初始状态"],['1',"在柜"],['2',"不在柜"]
			]
		});
		
		var  infoStroe= this.seleteGuideInforStore;

		this.gridPanel = Ext.create('Ext.grid.Panel', {
	      	id : 'guideShopPanel',
      		border:false,
      		enableTabScroll : true,
			autoScroll : true,
			anchor: "100% 100%",
			loadMask:{msg : '数据加载中...'},
			bbar : {
				xtype : 'pagingtoolbar',
				store : this.seleteGuideInforStore,
				displayInfo : true
			},
			columns :this.guideinfocolumns,
			store: this.seleteGuideInforStore,
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
						fieldLabel:"是否有效",
						xtype:"combo",
						labelWidth: 60,
						width : 120,
						labelAlign:"right",
						id:"shopValidBit",
						store: validBitCombo,
						valueField: 'validBitCode',
						displayField:'value',
						hiddenName:'validBitCode',
						triggerAction : 'all',
						name:'validBit',
						mode:'local'
					},
					{
						fieldLabel:"导购状态",
						xtype:"combo",
						id : "shopGuideStatusId",
						labelWidth: 60,
						width : 140,
						labelAlign:"right",
						store: guideStatusCombo,
						valueField: 'guideStatusCode',
						displayField:'value',
						hiddenName:'guideStatusCode',
						triggerAction : 'all',
						name:'guideStatus',
						mode:'local'
					},
					{
						id: "shopHealthCartEndtime",
						fieldLabel:"健康证有效期至",
						labelAlign: "right",
						xtype: "datefield",
						labelWidth: 90,
						labelAlign:"right",
						format: "Y-m-d",
						width:190
					},
					{
						id: "shopKitasEndtime",
						fieldLabel:"暂住证有效期至",
						labelAlign: "right",
						xtype: "datefield",
						labelWidth: 90,
						labelAlign:"right",
						format: "Y-m-d",
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
							infoStroe.load({
								params:{
									shopId : Ext.getCmp('shopStatisticsSid').getValue(),
									guideStatusId : Ext.getCmp('shopGuideStatusId').getValue(),
									healthCartEndtime : Ext.getCmp('shopHealthCartEndtime').getValue(),
									kitasEndtime : Ext.getCmp('shopKitasEndtime').getValue(),
									guideStatusId : Ext.getCmp('shopGuideStatusId').getValue(),
									validBit : Ext.getCmp('shopValidBit').getValue()
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
							var shopId = Ext.getCmp('shopStatisticsSid').getValue();
							var	guideStatusId = Ext.getCmp('shopGuideStatusId').getValue();
							var	healthCartEndtime =  Ext.util.Format.date(Ext.getCmp('shopHealthCartEndtime').getValue(),'Y-m-d');
							var	kitasEndtime = Ext.util.Format.date(Ext.getCmp('shopKitasEndtime').getValue(),'Y-m-d');
							var	validBit = Ext.getCmp('shopValidBit').getValue();
							
							var appWindow = window.open(_appctx+"/guideinfo/exportGuideinfoByShopToExcel.json?shopId="+shopId+
									"&guideStatusId="+guideStatusId+"&healthCartEndtime="+healthCartEndtime+"&kitasEndtime="+kitasEndtime
									+"&guideinfoStatus="+guideStatusId+"&validBit="+validBit+"&type=2");
							appWindow.focus();
						}
					}
					]}
				],
			listeners:{
				itemdblclick:function(view, record,item){
					 Ext.create('ShopinDesktop.GuideinfoUpdateWindow',{
					  	record : record,
					  	type : 'detail'
					}).show();
				}
			}
		});
		
//		shopStore.on('load', function (){
//			 var shopSid = parseInt(shopid);
//     		 Ext.getCmp('shopStatisticsSid').setValue(shopSid);    
//		});
		
		Ext.getCmp('shopValidBit').select(validBitCombo.getAt(0));
		Ext.getCmp('shopGuideStatusId').select(guideStatusCombo.getAt(0));
	}
});
