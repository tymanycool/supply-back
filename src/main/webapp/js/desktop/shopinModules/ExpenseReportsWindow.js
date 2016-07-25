Ext.define('ShopinDesktop.ExpenseReportsWindow', {
	extend : 'Ext.ux.desktop.Module',
	id : 'expenseReportsWindow',
	requires: ['Util.MonthField'],
	init : function(){
	},
	createWindow: function(){
		var me = this;
		this.initCmps(me);
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if(!win) {
			win = desktop.createWindow({
				id : me.id,
				title : '费用报表',
				width : '85%',
				height: '90%',
				autoscroll: true,
				layout: 'border',
				items : [
					this.treePanel,
					this.tabPanel
				]
			});
		};
		return win;
	},
	initCmps : function(me) {
		this.treePanel = Ext.create('Ext.tree.Panel', {
			id  : 'optTypeTree',
			title : '报表类型',
			width : '15%',
			border: 1,
			region: 'west',
			collapsible: true,
			rootVisible: false,
			root: {
				text: '报表类型',
				expanded: true,
				children: [
					{text: 'PAD费用表',   leaf: true, id: 'ExpensePadReport'},
					{text: '道具表',      leaf: true, id: 'ExpensePropReport'},
					{text: '道具表（供应商）',leaf: true, id: 'ExpensePropSupplyReport'},
					{text: '综合管理表',   leaf: true, id: 'ExpenseGenaralReport'},
					{text: '综合管理表(供应商)',   leaf: true, id: 'ExpenseGenaralSupplyReport'},
					{text: '定制面积修改日', leaf: true, id: 'ExpenseAreaChangeDays'}
				]
			},
			listeners: {
				itemclick: function(view, record,item){
					var itemId = record.data.id;
					var window = Ext.getCmp(itemId+'Window');
					if(window == undefined){
						if(itemId == 'ExpensePadReport'){
							Ext.getCmp('expenseStatisticalView').add(
								Ext.create('ShopinDesktop.ExpensePadReportWindow', {
									id : itemId+'Window',
									title: 'PAD费用统计',
									closable: true
								})
							);
							Ext.getCmp('expenseStatisticalView').setActiveTab(Ext.getCmp(itemId+'Window'));
						}
						if(itemId == 'ExpensePropReport'){
							Ext.getCmp('expenseStatisticalView').add(
								Ext.create('ShopinDesktop.ExpensePropReportWindow', {
									id : itemId+'Window',
									title: '道具费用统计',
									closable: true
								})
							);
							Ext.getCmp('expenseStatisticalView').setActiveTab(Ext.getCmp(itemId+'Window'));
						}
						if(itemId == 'ExpensePropSupplyReport'){
							Ext.getCmp('expenseStatisticalView').add(
								Ext.create('ShopinDesktop.ExpensePropSupplyReportWindow', {
									id : itemId+'Window',
									title: '道具费用统计（供应商）',
									closable: true
								})
							);
							Ext.getCmp('expenseStatisticalView').setActiveTab(Ext.getCmp(itemId+'Window'));
						}
						if(itemId == 'ExpenseGenaralReport'){
							Ext.getCmp('expenseStatisticalView').add(
								Ext.create('ShopinDesktop.ExpenseGenaralReportWindow', {
									id : itemId+'Window',
									title: '综合费用统计',
									closable: true
								})
							);
							Ext.getCmp('expenseStatisticalView').setActiveTab(Ext.getCmp(itemId+'Window'));
						}
						if(itemId == 'ExpenseGenaralSupplyReport'){
							Ext.getCmp('expenseStatisticalView').add(
								Ext.create('ShopinDesktop.ExpenseGenaralSupplyReportWindow', {
									id : itemId+'Window',
									title: '综合费用统计(供应商)',
									closable: true
								})
							);
							Ext.getCmp('expenseStatisticalView').setActiveTab(Ext.getCmp(itemId+'Window'));
						}
						if(itemId == 'ExpenseAreaChangeDays'){
							Ext.getCmp('expenseStatisticalView').add(
								Ext.create('ShopinDesktop.ExpenseAreaChangeDaysWindow', {
									id : itemId+'Window',
									title: '面积调整日操作',
									closable: true
								})
							);
							Ext.getCmp('expenseStatisticalView').setActiveTab(Ext.getCmp(itemId+'Window'));
						}
					} else {
						Ext.getCmp('expenseStatisticalView').setActiveTab(window);
					}
				}
			}
		});
		this.tabPanel = Ext.create('Ext.tab.Panel', {
			id : 'expenseStatisticalView',
			width: '85%',
			region: 'center',
			frame : true,
			enableTabScroll : true,
			autoScroll : true,	
			items : []
		});
	}
})