Ext.require('ShopinDesktop.ExpenseDic');
Ext.define('ShopinDesktop.ExpenseGenaralSupplyReportWindow', {
	extend: 'Ext.panel.Panel',
	requires: [],
	baseUrl : _appctx,
	id : 'expenseGenaralSupplyReportWindow',
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initComponents(me);
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title   : '综合费用统计（供应商）',
			layout: 'fit',
			items : [
				me.gridPanel
			]
		});
	},
	initComponents: function(me){
		
		var columns = [{xtype: 'rownumberer'}];
		columns.push({text: '门店ID', dataIndex: 'shopSid', width:90, sortable: true});
		columns.push({text: '供应商编码', dataIndex: 'supplySid', width:90, sortable: true});
		columns.push({text: '供应商名称', dataIndex: 'supplyName', width:180, sortable: true});
		columns.push({text: '新面积新收费标准', dataIndex: 'totalExpense', width:100, sortable: true});
		
		this.expenseGenaralSupplyStore = Ext.create('Ext.data.JsonStore', {
			fields: [
				{name: 'sid', type: 'int'},
				{name: 'shopSid', type: 'string'},
				{name: 'supplySid', type: 'string'},
				{name: 'brandSid', type: 'string'},
				{name: 'supplyName', type: 'string'},
				{name: 'totalExpense', type: 'float'}
			],
			pageSize : 45,
			proxy: {
				type: 'ajax',
				api: {
					read: _appctx + '/genaralstatis/queryExpenseSupplyInfo.json',
					excel : _appctx + '/genaralstatis/exportSupplyExcelTable.json'
				},
				timeout: 3000000,
				getMethod: function(){
					return 'POST';
				},
				reader : {
					type : 'json',
					root : 'list',
					totalProperty :'totalCount'
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(store.proxy.extraParams, {
						shopId : Ext.getCmp('expenseGenaralSupplyShop').getValue(),
						supplyId : Ext.getCmp('expenseGenaralSupplySupply').getValue(),
						month : Ext.getCmp('expenseGenaralSupplyMonth').getValue(),
						purchaseId : Ext.getCmp('expenseGenaralSupplyPurchase').getValue()
					})
				}
			}
		});
		if(!ShopinDesktop.ExpenseDic.coll.get('allShopInfo')){
			Ext.Ajax.request({
				url : _appctx + '/ssd/getAllShopInfo.json',
				async: false, 
				success: function(response, opts) {
			        ShopinDesktop.ExpenseDic.coll.add('allShopInfo', eval(response.responseText));
			    }
			})
		};
		if(!ShopinDesktop.ExpenseDic.coll.get('allBrands')){
			Ext.Ajax.request({
				url : _appctx + '/ssd/getAllBrandsErp.json',
				async: false, 
				success: function(response, opts) {
			        ShopinDesktop.ExpenseDic.coll.add('allBrands', eval(response.responseText));
			    }
			})
		};
		if(!ShopinDesktop.ExpenseDic.coll.get('allSupplies')){
			Ext.Ajax.request({
				url : _appctx + '/ssd/getAllSupplies.json',
				async: false, 
				success: function(response, opts) {
			        ShopinDesktop.ExpenseDic.coll.add('allSupplies', eval(response.responseText));
			    }
			})
		};
		if(!ShopinDesktop.ExpenseDic.coll.get('firstErpLevelClass')){
			Ext.Ajax.request({
				url : _appctx + '/ssd/getFirstLevelClass.json',
				params :{
					source : 1
				},
				async: false, 
				success: function(response, opts) {
			        ShopinDesktop.ExpenseDic.coll.add('firstErpLevelClass', eval(response.responseText));
			    }
			})
		};
		this.allShopStore = Ext.create("Ext.data.Store", {
			fields: ["show", "value"],
			data  : ShopinDesktop.ExpenseDic.coll.get('allShopInfo'),
			autoLoad: true
		});
		this.productBrandStore = Ext.create("Ext.data.Store", {
			fields: ["show", "value"],
			data  : ShopinDesktop.ExpenseDic.coll.get('allBrands'),
			autoLoad: true
		});
		this.supplyStore = Ext.create("Ext.data.Store", {
			fields: ["show", "value"],
			data  : ShopinDesktop.ExpenseDic.coll.get('allSupplies'),
			autoLoad: true
		});
		this.purchaseStore = Ext.create('Ext.data.Store', {
			fields: ['show', 'value'],
			data  : [
				{show: '采购一部', value: '1'},
				{show: '采购二部', value: '2'},
				{show: '内衣小组', value: '3'},
				{show: '租赁', value: '4'}
			]
		});
		
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'expenseGenaralSupplyRecordGrid',
			border:false,
      		enableTabScroll : true,
			autoScroll : true,
			columnLines: true,
			viewConfig: {
				enableTextSelection: true
			},
			loadMask: {msg: '数据加载中...'},
			dockedItems: {
				xtype: 'pagingtoolbar',
				dock : 'bottom',
				store: this.expenseGenaralSupplyStore,
				displayInfo: true
			},
			store: this.expenseGenaralSupplyStore,
			columns: columns,
			selType: 'rowmodel',
			tbar: {
				xtype: 'buttongroup',
				width: '100%',
				columns: 7,
				items: [{
					xtype: 'combo',
					id : 'expenseGenaralSupplyShop',
					fieldLabel: '门店',
					labelWidth: 30,
					labelAlign: 'right',
					editable:true,
					queryMode: "local",
					width: 120,
					store: this.allShopStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					xtype: 'combo',
					id : 'expenseGenaralSupplySupply',
					fieldLabel: '供应商',
					labelWidth: 40,
					editable:true,
					queryMode: "local",
					labelAlign: 'right',
					width: 200,
					store: this.supplyStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					id: "expenseGenaralSupplyMonth",
					name: "month",
					fieldLabel:"月份",
					labelAlign: "right",
					xtype: "monthfield",
					labelWidth: 40,
					width: 150,
					format: "Y年m月      d日",
					allowBlank: false,
					blankText : '月份不能为空',
					editable  : false,
					value: new Date()
				}, {
					xtype: 'combo',
					id : 'expenseGenaralSupplyPurchase',
					fieldLabel: '采购中心',
					labelWidth: 60,
					editable:true,
					queryMode: "local",
					labelAlign: 'right',
					width: 160,
					store: this.purchaseStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					xtype: "button",
					icon: _appctx + "/images/find.png",
					pressed: true,
					text: "查询",
					aligh : "right",
					width:50,
					margin:"0 10 0 10",
					handler: function() {
						me.expenseGenaralSupplyStore.load();
					}
				}, {
					xtype : 'button',
					text  : '导出Excel',
					pressed: true,
					aligh : "right",
					width:70,
					margin:"0 10 0 10",
					handler : function(button, e){
						var a = document.createElement('a');
						document.body.appendChild(a);
						var date = Ext.getCmp('expenseGenaralSupplyMonth').value;
						var year = date.getFullYear().toString()
						var month = date.getMonth()+1;
						if(month<10){
							month = '0'+month;
						}
						a.href = me.expenseGenaralSupplyStore.getProxy().api.excel+'?month='+year+'-'+month;;
						a.target = '_blank';
						a.download = '综合费用费用明细(供应商).xls';
						a.click();
					}
				}]
			}
		});
	}
})