Ext.require('ShopinDesktop.ExpenseDic');
Ext.define('ShopinDesktop.ExpenseGenaralReportWindow', {
	extend: 'Ext.panel.Panel',
	requires: [],
	baseUrl : _appctx,
	id : 'expenseGenaralReportWindow',
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title   : '综合费用统计',
			layout: 'fit',
			tbar: {
				xtype: 'buttongroup',
				width: '100%',
				columns: 7,
				items: [{
					xtype: 'combo',
					id : 'expenseGenaralShop',
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
					id : 'expenseGenaralBrand',
					fieldLabel: '品牌',
					labelWidth: 30,
					editable:true,
					queryMode: "local",
					labelAlign: 'right',
					width: 200,
					store: this.productBrandStore,
					valueField: 'value',
					displayField: 'show'
				}, {
					xtype: 'combo',
					id : 'expenseGenaralSupply',
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
					id: "expenseGenaralMonth",
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
					id : 'expenseGenaralPurchase',
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
						columns.push({text: '门店ID', dataIndex: 'shopSid', width:60, sortable: true});
						columns.push({text: '品类', dataIndex: 'categoryName', width:60, sortable: true});
						columns.push({text: '供应商编码', dataIndex: 'supplySid', width:90, sortable: true});
						columns.push({text: '供应商名称', dataIndex: 'supplyName', width:180, sortable: true});
						columns.push({text: '品牌', dataIndex: 'brand', width:100, sortable: true});
						Ext.Ajax.request({
							url: b.ownerCt.ownerCt.expenseGenaralStore.getProxy().api.changeDay,
							params: {
						        month: Ext.getCmp('expenseGenaralMonth').value
						    },
						    async:false,
						    success: function(response){
						    	var array = Ext.JSON.decode(response.responseText);
						    	Ext.Array.each(array, function(item, index){
						    		var no = index + 1;
						    		columns.push({xtype:'gridcolumn', text: item+'日', 
										columns: [
											{text: '经营面积', dataIndex: 'area'+no, width: 60},
											{text: '收费面积', dataIndex: 'expenseArea'+no, width: 60},
											{text: '天数', dataIndex: 'days'+no, width: 40}
										]
									});
						    	})
						    }
						});
						
						columns.push({text: '新收费标准', dataIndex: 'newExpenseStatus', width:70, editor: {xtype: 'textfield',allowBlank: false}});
						columns.push({text: '新面积新收费标准', dataIndex: 'totalExpense', width:100, sortable: true});
						
						var oldStatus;
						var gridPanel = Ext.create('Ext.grid.Panel', {
							id : 'expenseGenaralRecordGrid',
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
								store: b.ownerCt.ownerCt.expenseGenaralStore,
								displayInfo: true
							},
							store: b.ownerCt.ownerCt.expenseGenaralStore,
							columns: columns,
							selType: 'rowmodel',
							plugins: [
						        Ext.create('Ext.grid.plugin.RowEditing', {
						            clicksToEdit: 2,
						            listeners: {
						            	beforeedit: function(editor, context, eOpts){
						            		oldStatus = context.record.get('newExpenseStatus');
						            	},
						            	edit: function(editor, context, eOpts){
						            		Ext.Ajax.request({
						            			url: context.grid.getStore().getProxy().api.update,
						            			params : {
						            				newExpenseStatus : context.record.get('newExpenseStatus'),
						            				supplySid : context.record.get('supplySid'),
						            				shopSid : context.record.get('shopSid'),
						            				brandSid : context.record.get('brandSid')
						            			},
						            			success: function(response, opts) {
						            				var record = context.record;
						            				var newStatus = record.get('newExpenseStatus');
											        record.set('totalExpense', record.get('totalExpense')*(newStatus/oldStatus));
											        record.commit();
											    },
											    failure: function(response, opts) {
											        alert('修改失败，失败状态：' + response.status);
											    }	
						            		});
						            	}
						            }
						        })
						    ]
						});
						b.ownerCt.ownerCt.removeAll(true);
						b.ownerCt.ownerCt.add(gridPanel);
						b.ownerCt.ownerCt.expenseGenaralStore.load();
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
						var date = Ext.getCmp('expenseGenaralMonth').value;
						var year = date.getFullYear().toString()
						var month = date.getMonth()+1;
						if(month<10){
							month = '0'+month;
						}
						a.href = button.ownerCt.ownerCt.expenseGenaralStore.getProxy().api.excel+'?month='+year+'-'+month;;
						a.target = '_blank';
						a.download = '综合费用费用明细.xls';
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
		this.expenseGenaralStore = Ext.create('Ext.data.JsonStore', {
			fields: [
				{name: 'sid', type: 'int'},
				{name: 'shopSid', type: 'string'},
				{name: 'categoryName', type: 'string'},
				{name: 'supplySid', type: 'string'},
				{name: 'brandSid', type: 'string'},
				{name: 'supplyName', type: 'string'},
				{name: 'brand', type: 'string'},
				{name: 'area1', type: 'float'},
				{name: 'expenseArea1', type: 'float'},
				{name: 'days1', type: 'int'},
				{name: 'area2', type: 'float'},
				{name: 'expenseArea2', type: 'float'},
				{name: 'days2', type: 'int'},
				{name: 'area3', type: 'float'},
				{name: 'expenseArea3', type: 'float'},
				{name: 'days3', type: 'int'},
				{name: 'area4', type: 'float'},
				{name: 'expenseArea4', type: 'float'},
				{name: 'days4', type: 'int'},
				{name: 'newExpenseStatus', type: 'float'},
				{name: 'totalExpense', type: 'float'}
			],
			pageSize : 45,
			proxy: {
				type: 'ajax',
				api: {
					read: _appctx + '/genaralstatis/queryExpenseInfo.json',
					excel : _appctx + '/genaralstatis/exportExcelTable.json',
					update : _appctx + '/genaralstatis/updateExpenseStatus.json',
					changeDay: _appctx + '/genaralstatis/changeDay.json'
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
						shopId : Ext.getCmp('expenseGenaralShop').getValue(),
						brand : Ext.getCmp('expenseGenaralBrand').getValue(),
						supplyId : Ext.getCmp('expenseGenaralSupply').getValue(),
						month : Ext.getCmp('expenseGenaralMonth').getValue(),
						purchaseId : Ext.getCmp('expenseGenaralPurchase').getValue()
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