Ext.require('ShopinDesktop.ExpenseDic');
Ext.define('ShopinDesktop.ExpensePropSupplyReportWindow', {
	extend: 'Ext.panel.Panel',
	requires: ['Util.MonthField'],
	baseUrl : _appctx,
	id : 'expensePropSupplyReportWindow',
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title   : '道具费用统计(供应商)',
			layout: 'fit',
			tbar: {
				xtype: 'buttongroup',
				width: '100%',
				columns: 7,
				items: [{
					xtype: 'combo',
					id : 'expensePropSupplyShop',
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
					id : 'expensePropSupplySupply',
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
					id: "expensePropSupplyMonth",
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
					id : 'expensePropSupplyPurchase',
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
					handler: function(b) {
						var columns = [{xtype: 'rownumberer'}];
//						columns.push({text: '品类', dataIndex: 'categoryName', width:60, sortable: true});
						columns.push({text: '门店ID', dataIndex: 'shopSid', width:60, sortable: true});
						columns.push({text: '供应商编码', dataIndex: 'supplySid', width:90, sortable: true});
						columns.push({text: '供应商名称', dataIndex: 'supplyName', width:180, sortable: true});
						Ext.Ajax.request({
							url: b.ownerCt.ownerCt.expensePropSupplyStore.getProxy().api.changeDay,
							params: {
						        month: Ext.getCmp('expensePropSupplyMonth').value
						    },
						    async:false,
						    success: function(response){
						    	var array = Ext.JSON.decode(response.responseText);
						    	Ext.Array.each(array, function(item, index){
						    		var no = index + 1;
						    		columns.push({xtype:'gridcolumn', text: item+'日', 
										columns: [
											{text: '经营面积', dataIndex: 'area'+no, width: 60},
											{text: '天数', dataIndex: 'days'+no, width: 40},
											{text: '新收费标准', dataIndex: 'expStatus'+no, width: 70}
										]
									});
						    	})
						    }
						});
						
						columns.push({text: '新面积新收费标准', dataIndex: 'totalExpense', width:100, sortable: true});
						
						var gridPanel = Ext.create('Ext.grid.Panel', {
							id : 'expensePropSupplyRecordGrid',
							border:false,
				      		enableTabScroll : true,
							autoScroll : true,
							columnLines: true,
							viewConfig: {
								enableTextSelection: true
							},
							dockedItems: {
								xtype: 'pagingtoolbar',
								dock : 'bottom',
								store: b.ownerCt.ownerCt.expensePropSupplyStore,
								displayInfo: true
							},
							store: b.ownerCt.ownerCt.expensePropSupplyStore,
							columns: columns
						});
						b.ownerCt.ownerCt.removeAll(true);
						b.ownerCt.ownerCt.add(gridPanel);
						b.ownerCt.ownerCt.expensePropSupplyStore.load();
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
						var date = Ext.getCmp('expensePropSupplyMonth').value;
						var year = date.getFullYear().toString()
						var month = date.getMonth()+1;
						if(month<10){
							month = '0'+month;
						}
						a.href = button.ownerCt.ownerCt.expensePropSupplyStore.getProxy().api.excel+'?month='+year+'-'+month;
						a.target = '_blank';
						a.download = '道具费用明细(供应商).xls';
						a.click();
					}
				}]
			},
			items : [
//				me.gridPanel
			]
		});
	},
	initComponents: function(me){
		this.expensePropSupplyStore = Ext.create('Ext.data.JsonStore', {
			fields: [
				{name: 'sid', type: 'int'},
				{name: 'shopSid', type: 'string'},
//				{name: 'categoryName', type: 'string'},
				{name: 'supplySid', type: 'string'},
				{name: 'supplyName', type: 'string'},
				{name: 'brand', type: 'string'},
				{name: 'area1', type: 'float'},
				{name: 'days1', type: 'int'},
				{name: 'expStatus1', type: 'int'},
				{name: 'area2', type: 'float'},
				{name: 'days2', type: 'int'},
				{name: 'expStatus2', type: 'int'},
				{name: 'area3', type: 'float'},
				{name: 'days3', type: 'int'},
				{name: 'expStatus3', type: 'int'},
				{name: 'area4', type: 'float'},
				{name: 'days4', type: 'int'},
				{name: 'expStatus4', type: 'int'},
                {name: 'area5', type: 'float'},{name: 'days5', type: 'int'},{name: 'expStatus5', type: 'int'},
                {name: 'area6', type: 'float'},{name: 'days6', type: 'int'},{name: 'expStatus6', type: 'int'},
                {name: 'area7', type: 'float'},{name: 'days7', type: 'int'},{name: 'expStatus7', type: 'int'},
                {name: 'area8', type: 'float'},{name: 'days8', type: 'int'},{name: 'expStatus8', type: 'int'},
                {name: 'area9', type: 'float'},{name: 'days9', type: 'int'},{name: 'expStatus9', type: 'int'},
                {name: 'area10', type: 'float'},{name: 'days10', type: 'int'},{name: 'expStatus10', type: 'int'},
                {name: 'area11', type: 'float'},{name: 'days11', type: 'int'},{name: 'expStatus11', type: 'int'},
                {name: 'area12', type: 'float'},{name: 'days12', type: 'int'},{name: 'expStatus12', type: 'int'},
                {name: 'area13', type: 'float'},{name: 'days13', type: 'int'},{name: 'expStatus13', type: 'int'},
                {name: 'area14', type: 'float'},{name: 'days14', type: 'int'},{name: 'expStatus14', type: 'int'},
                {name: 'area15', type: 'float'},{name: 'days15', type: 'int'},{name: 'expStatus15', type: 'int'},
                {name: 'area16', type: 'float'},{name: 'days16', type: 'int'},{name: 'expStatus16', type: 'int'},
                {name: 'area17', type: 'float'},{name: 'days17', type: 'int'},{name: 'expStatus17', type: 'int'},
                {name: 'area18', type: 'float'},{name: 'days18', type: 'int'},{name: 'expStatus18', type: 'int'},
                {name: 'area19', type: 'float'},{name: 'days19', type: 'int'},{name: 'expStatus19', type: 'int'},
                {name: 'area20', type: 'float'},{name: 'days20', type: 'int'},{name: 'expStatus20', type: 'int'},
                {name: 'totalExpense', type: 'int'},
			],
			pageSize : 45,
			proxy: {
				type: 'ajax',
				api: {
					read: _appctx + '/propstatis/querySupplyExpenseInfo.json',
					excel : _appctx + '/propstatis/exportSupplyExcelTable.json',
					changeDay: _appctx + '/propstatis/changeDay.json'
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
						shopId : Ext.getCmp('expensePropSupplyShop').getValue(),
						supplyId : Ext.getCmp('expensePropSupplySupply').getValue(),
						month : Ext.getCmp('expensePropSupplyMonth').getValue(),
						purchaseId : Ext.getCmp('expensePropSupplyPurchase').getValue()
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
	}
})