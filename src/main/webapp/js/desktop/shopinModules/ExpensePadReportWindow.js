Ext.require('ShopinDesktop.ExpenseDic');
Ext.define('ShopinDesktop.ExpensePadReportWindow', {
	extend: 'Ext.panel.Panel',
	requires: ['Util.MonthField'],
	baseUrl : _appctx,
	id : 'expensePadReportWindow',
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title   : 'PAD费用统计',
			layout: 'fit',
			items : [
				me.gridPanel
			]
		});
	},
	initComponents: function(me){
		var expensePadStore = Ext.create('Ext.data.JsonStore', {
			fields: [
				{name: 'sid', type: 'int'},
				{name: 'shopSid', type: 'string'},
				{name: 'categoryName', type: 'string'},
				{name: 'supplySid', type: 'string'},
				{name: 'supplyName', type: 'string'},
				{name: 'brand', type: 'string'},
				{name: 'brandSid', type: 'string'},
				{name: 'busPractice', type: 'string'},
				{name: 'expenseSource', type: 'string'},
				{name: 'padNum', type: 'int'},
				{name: 'expensStandard', type: 'int'},
				{name: 'startDate', convert : function convertDate(v, record) {
					if (v == null) {
						return null;
					}
					var date=new Date(v);
					return Ext.Date.format(date,'Y-m-d');
				}},
				{name: 'stopDate', convert : function convertDate(v, record) {
					if (v == null) {
						return null;
					}
					var date=new Date(v);
					return Ext.Date.format(date,'Y-m-d');
				}},
				{name: 'daysOfUsed', type: 'int'},
				{name: 'predictExpense', type: 'int'},
				{name: 'totalExpens', type: 'int'},
				{name: 'memo', type: 'string'}
			],
			pageSize : 45,
			proxy: {
				type: 'ajax',
				api: {
					read: _appctx + '/padstatis/queryExpenseInfo.json',
					update: _appctx + '/padstatis/updateExpenseInfo.json',
					excel : _appctx + '/padstatis/exportExcelTable.json'
				},
				getMethod: function(){
					return 'POST';
				},
				timeout: 3000000,
				reader : {
					type : 'json',
					root : 'list',
					totalProperty :'totalCount'
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(store.proxy.extraParams, {
						shopId : Ext.getCmp('expensePadShop').getValue(),
						brand : Ext.getCmp('expensePadBrand').getValue(),
						supplyId : Ext.getCmp('expensePadSupply').getValue(),
						month : Ext.getCmp('expensePadMonth').getValue(),
						purchaseId : Ext.getCmp('expensePadPurchase').getValue()
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
		var allShopStore = Ext.create("Ext.data.Store", {
			fields: ["show", "value"],
			data  : ShopinDesktop.ExpenseDic.coll.get('allShopInfo'),
			autoLoad: true
		});
		var productBrandStore = Ext.create("Ext.data.Store", {
			fields: ["show", "value"],
			data  : ShopinDesktop.ExpenseDic.coll.get('allBrands'),
			autoLoad: true
		});
		var supplyStore = Ext.create("Ext.data.Store", {
			fields: ["show", "value"],
			data  : ShopinDesktop.ExpenseDic.coll.get('allSupplies'),
			autoLoad: true
		});
		var purchaseStore = Ext.create('Ext.data.Store', {
			fields: ['show', 'value'],
			data  : [
				{show: '采购一部', value: '1'},
				{show: '采购二部', value: '2'},
				{show: '内衣小组', value: '3'},
				{show: '租赁', value: '4'}
			]
		});
		this.recordColumns = [
			{xtype: 'rownumberer'},
			{text: '门店ID', dataIndex: 'shopSid', width:60, sortable: true},
			{text: '柜组编码', dataIndex: 'categoryName', width:60, sortable: true},
			{text: '供应商编码', dataIndex: 'supplySid', width:90, sortable: true},
			{text: '供应商名称', dataIndex: 'supplyName', width:180, sortable: true},
			{text: '品牌', dataIndex: 'brand', width:100, sortable: true},
			{text: '经营方式', dataIndex: 'busPractice', width:60, sortable: true},
			{text: '费用项目', dataIndex: 'expenseSource', width:80},
			{text: '使用台数', dataIndex: 'padNum', width:60, sortable: true,
				editor: {
	                xtype: 'textfield',
	                allowBlank: false
	            }
            },
			{text: '计费标准', dataIndex: 'expensStandard', width:60},
			{text: '起始日期', dataIndex: 'startDate', width:80, sortable: true},
			{text: '截止日期', dataIndex: 'stopDate', width:80, sortable: true},
			{text: '使用天数', dataIndex: 'daysOfUsed', width:60, sortable: true},
			{text: '应收费用', dataIndex: 'predictExpense', width:60, sortable: true},
			{text: '合计费用', dataIndex: 'totalExpens', width:60},
			{text: '备注', dataIndex: 'memo', width:100}
		];
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'expensePadRecordGrid',
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
				store: expensePadStore,
				displayInfo: true
			},
			store: expensePadStore,
			columns: this.recordColumns,
			selType: 'rowmodel',
			plugins: [
		        Ext.create('Ext.grid.plugin.RowEditing', {
		            clicksToEdit: 2,
		            listeners: {
		            	edit: function(editor, context, eOpts){
		            		Ext.Ajax.request({
		            			url: context.grid.getStore().getProxy().api.update,
		            			params : {
		            				padNum : context.record.get('padNum'),
		            				supplySid : context.record.get('supplySid'),
		            				shopSid : context.record.get('shopSid'),
		            				brandSid : context.record.get('brandSid')
		            			},
		            			success: function(response, opts) {
		            				var record = context.record;
		            				var nums = record.get('padNum');
		            				var days = record.get('daysOfUsed');
							        record.set('predictExpense', nums*days*10);
							        record.set('totalExpens', nums*days*10);
							        record.commit();
							    },
							    failure: function(response, opts) {
							        alert('修改失败，失败状态：' + response.status);
							    }	
		            		});
		            	}
		            }
		        })
		    ],
			tbar: {
				xtype: 'buttongroup',
				width: '100%',
				columns: 7,
				items: [{
					xtype: 'combo',
					id : 'expensePadShop',
					fieldLabel: '门店',
					labelWidth: 30,
					labelAlign: 'right',
					editable:true,
					queryMode: "local",
					width: 120,
					store: allShopStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					xtype: 'combo',
					id : 'expensePadBrand',
					fieldLabel: '品牌',
					labelWidth: 30,
					editable:true,
					queryMode: "local",
					labelAlign: 'right',
					width: 200,
					store: productBrandStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					xtype: 'combo',
					id : 'expensePadSupply',
					fieldLabel: '供应商',
					labelWidth: 40,
					editable:true,
					queryMode: "local",
					labelAlign: 'right',
					width: 200,
					store: supplyStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					id: "expensePadMonth",
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
					value: new Date(),
					width:150
				}, {
					xtype: 'combo',
					id : 'expensePadPurchase',
					fieldLabel: '采购中心',
					labelWidth: 60,
					editable:true,
					queryMode: "local",
					labelAlign: 'right',
					width: 160,
					store: purchaseStore,
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
							expensePadStore.load();
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
						a.href = button.up('grid').getStore().getProxy().api.excel;
						a.target = '_blank';
						a.download = 'PAD费用明细.xls';
						a.click();
					}
				}]
			}
		})
	}
})