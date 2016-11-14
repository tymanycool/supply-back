Ext.define('ShopinDesktop.CashierReportsWindow', {
	extend: 'Ext.ux.desktop.Module',
	id:'cashierReportsWindow',
	init : function(){
//		this.launcher = {
//			text: '统计查询',
//			iconCls:'ctg-icon'
//		};
	},

	createWindow : function(){
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('cashierReportsWindow');
		var treeStore = Ext.create("Ext.data.TreeStore",{
			root:{
				cls : 'feeds-node',
				expanded : true,
				children:[{
					text: "流水报表查询",
					iconCls:'button_tree',
					leaf: true,
					id:"cashierReportSelectWindow"
				},{
					text: "收银员长短款报表查询",
					iconCls:'button_tree',
					leaf: true,
					id:"longShortItermReportWindow"
				}]
			}
		});
		if(!win){
			win = desktop.createWindow({
				id: 'statisticalQueryViewWindow',
				title:'报表查询',
				width:1200,
				height:610,
				iconCls: 'ctg-icon',
				animCollapse:false,
				constrainHeader:true,
				layout: 'border',
				items: [Ext.create("Ext.tree.TreePanel",{ 
					title : '导航管理',
			        region:'west',
			        split:true,
			        width: "15%",
			        collapsible:true,
			        store : treeStore,
			        rootVisible : false,
					  listeners:{
						  itemclick:function(view, record,item){
							  var newId = record.data.id;
							  var oldId =Ext.getCmp(newId+1);
							  if (oldId == undefined) {
//								   if(isAuth(newId)){
 									if(newId=="cashierReportSelectWindow"){
									  Ext.getCmp('statisticalView').add(
										Ext.create("ShopinDesktop.CashierSelectWindow", {
											id: "cashierReportSelectWindow1",
											title:"收银员流水报表查询",
											baseUrl: _appctx,
											itemsPerPage: 20,
											width: "80%",
											region: "center",
											closable:true
										}).show()
									  );
									  Ext.getCmp('statisticalView').setActiveTab(Ext.getCmp("cashierReportSelectWindow1"));
								  }else if(newId=="longShortItermReportWindow"){
									  Ext.getCmp('statisticalView').add(
												Ext.create("ShopinDesktop.LongShortItemWindow", {
													id: "longShortItermReportWindow1",
													title:"收银员长短款报表查询",
													baseUrl: _appctx,
													itemsPerPage: 20,
													width: "80%",
													region: "center",
													closable:true
												})
											  );
											  Ext.getCmp('statisticalView').setActiveTab(Ext.getCmp("longShortItermReportWindow1"));
										  }

							 }else{
								 Ext.getCmp('statisticalView').setActiveTab(oldId);
							 }
						  }
					  }
			       }),Ext.create("Ext.TabPanel",{
						id:"statisticalView",
						width: "85%",
						region:'center',//中间面板
						activeTab: 0,
						enableTabScroll : true,
						autoScroll : true,						
			 			frame:true, 
			 			items:[{}
			 			]
				})
				        ]
			});
		}
		return win;
	}
});

