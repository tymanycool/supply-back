Ext.define('ShopinDesktop.ExpenseAreaChangeDaysWindow', {
	extend: 'Ext.panel.Panel',
	requires: ['Util.MonthField'],
	baseUrl : _appctx,
	id : 'expenseAreaChangeDaysWindow',
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title   : '面积调整日操作',
			layout: 'fit',
			items : [
				me.gridPanel
			]
		});
	},
	initComponents: function(me){
		var coll = Ext.create('Ext.util.MixedCollection', {});
		var changeDaysStore = Ext.create('Ext.data.JsonStore', {
			fields: [
				{name: 'sid', type: 'int'},
				{name: 'month', convert : function convertDate(v, record) {
					if (v == null) {
						return null;
					}
					var date=new Date(v);
					return Ext.Date.format(date,'Y年m月');
				}},
				{name: 'changeDay', type: 'string'},
				{name: 'memo', type: 'string'}
			],
			proxy: {
				type: 'ajax',
				api: {
					read: _appctx + '/changeday/queryExpenseInfo.json',
					destroy: _appctx + '/changeday/removeChangeDay.json',
					update: _appctx + '/changeday/updateChangeDay.json',
					create: _appctx + '/changeday/createChangeDay.json'
				},
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
						month : Ext.getCmp('expenseChangeMonth').getValue()
					})
				}
			}
		});
		
		this.recordColumns = [ 
			{xtype: 'rownumberer'},
			{dataIndex : 'sid', hidden : true, hideable : false}, 
			{text : '月份', dataIndex : 'month'}, 
			{text : '修改日', dataIndex : 'changeDay', width: 60, editor : {
					xtype : 'textfield',
					regex: /^[0-2][0-9]$|^[3][0-1]$/,
					regexText : '请输入正确的日期(01~31)',
					allowBlank : false,
					blankText : '修改日不能为空'
				}
			}, {text : '备注', dataIndex : 'memo', editor : {
					xtype : 'textfield',
					allowBlank : true
				}
			}, {
				xtype : 'actioncolumn',
				text : '操作',
				width : 50,
				hideable : false,
				items : [{
					icon : _appctx+'/images/remove.gif',
					tooltip : '删除',
					handler : function(grid, rowIndex, colIndex) {
						var rec = grid.getStore().getAt(rowIndex);
						Ext.Msg.confirm('删除？', '确实删除?', function(buttonId){
							if (buttonId == 'ok' || buttonId == 'yes') {
								grid.getStore().remove(rec);
								grid.getStore().sync({
									success : function(batch, options) {
										changeDaysStore.loadPage(1);
									},
									failure : function(batch, options) {
										if (options.operations.destroy) {
											Ext.Msg.alert("操作失败", "删除修改日失败");
										}
										changeDaysStore.loadPage(1);
									}
								});
							}
						});
					}
				}]
			}
		]
		
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'expenseChangeDayRecordGrid',
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
				store: changeDaysStore,
				displayInfo: true
			},
			store: changeDaysStore,
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
		            				sid : context.record.get('sid'),
		            				changeDay : context.record.get('changeDay'),
		            				memo: context.record.get('memo')
		            			},
		            			success: function(response, opts) {
							        context.grid.getStore().loadPage(1);
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
					id: "expenseChangeMonth",
					name: "month",
					fieldLabel:"请选择月份",
					labelAlign: "right",
					xtype: "monthfield",
					labelWidth: 80,
					width: 150,
					format: "Y年m月      d日",
					allowBlank: false,
					blankText : '月份不能为空',
					editable  : false,
					value: new Date(),
					width:200
				}, {
					xtype: "button",
					icon: _appctx + "/images/find.png",
					pressed: true,
					text: "查询",
					aligh : "right",
					width:50,
					margin:"0 10 0 10",
					handler: function() {
						changeDaysStore.load();
					}
				}, {
					xtype : 'button',
					text  : '新增修改日',
					pressed: true,
					aligh : "right",
					width:70,
					margin:"0 10 0 10",
					handler : function(button, e){
						var win = Ext.create('Ext.window.Window', {
							title: '请选择修改日期',
							id  : 'addChangeDay',
							width: 350,
							height: 240,
							items: [{
								xtype: 'datefield',
								text : 'newChangeDay',
								margin:"10 10 10 10",
								labelWidth: 60,
								width: 245,
								fieldLabel: '修改日'
							}, {
								xtype: 'textareafield',
								text : 'changeDayMemo',
								autoScroll: true,
								margin:"10 10 10 10",
								labelWidth: 60,
								width: 300,
								height: 100,
								fieldLabel: '备注'
							}],
							buttons: [{
								pressed: true,
								text: "保存",
								aligh : "right",
								width:50,
								margin:"0 10 0 10",
								handler: function(button) {
									Ext.Ajax.request({
										url: changeDaysStore.getProxy().api.create,
										params: {
											changeDay: button.up('window').down('datefield').value,
											memo: button.up('window').down('textareafield').value
										},
										success: function(response, opts) {
											changeDaysStore.loadPage(1);
											Ext.MessageBox.alert('成功', '新增修改日成功！');
									        Ext.getCmp('addChangeDay').close();
									    },
									    failure: function(response, opts) {
									    	Ext.MessageBox.alert('失败', '新增修改日失败！');
									    }
									});
								}
							}, {
								pressed: true,
								text: "取消",
								aligh : "right",
								width:50,
								margin:"0 10 0 10",
								handler: function(button) {
									Ext.getCmp('addChangeDay').close();
								}
							}]
						});
						Ext.getCmp('expenseReportsWindow').add(win);
						win.show();
					}
				}]
			}
		})
	}
})