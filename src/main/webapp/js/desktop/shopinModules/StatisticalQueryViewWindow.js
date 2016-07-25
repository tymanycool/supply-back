Ext.define('ShopinDesktop.StatisticalQueryViewWindow', {
	extend: 'Ext.ux.desktop.Module',
	id:'statisticalQueryViewWindow',
	init : function(){
//		this.launcher = {
//			text: '统计查询',
//			iconCls:'ctg-icon'
//		};
	},

	createWindow : function(){
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('statisticalQueryViewWindow');
		var treeStore = Ext.create("Ext.data.TreeStore",{
			root:{
				cls : 'feeds-node',
				expanded : true,
				children:[{
					text: "按门店统计",
					iconCls:'button_tree',
					leaf: true,
					id:"guideShopStatisticsWindow"
				},{
					text: "按供应商统计",
					iconCls:'button_tree',
					leaf: true,
					id:"guideSupplyStatisticsWindow"
				},{
					//add by qutengfei for 操作日志查询（supply_guide_log） in 20150714 begin
					/**
					* 导购手动变价权限日志报表
					* for demand
					* feature https://tower.im/s/beCeH
					* author qutengfei
					*/
					text: "操作日志查询",
					iconCls:'button_tree',
					leaf: true,
					id:"operaLogManagementWindow"
				}
					//add by qutengfei for 操作日志查询（supply_guide_log） in 20150714 
				]
			}
		});
		if(!win){
			win = desktop.createWindow({
				id: 'statisticalQueryViewWindow',
				title:'统计查询',
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
			        width: "20%",
			        collapsible:true,
			        store : treeStore,
			        rootVisible : false,
					  listeners:{
						  itemclick:function(view, record,item){
							  var newId = record.data.id;
							  var oldId =Ext.getCmp(newId+1);
							  if (oldId == undefined) {
//								   if(isAuth(newId)){
 									if(newId=="guideShopStatisticsWindow"){
									  Ext.getCmp('statisticalView').add(
										Ext.create("ShopinDesktop.GuideShopStatisticsWindow", {
											id: "guideShopStatisticsWindow1",
											title:"按门店统计",
											baseUrl: _appctx,
											itemsPerPage: 20,
											width: "80%",
											region: "center",
											closable:true
										}).show()
									  );
									  Ext.getCmp('statisticalView').setActiveTab(Ext.getCmp("guideShopStatisticsWindow1"));
								  }else if(newId=="guideSupplyStatisticsWindow"){
									  Ext.getCmp('statisticalView').add(
										Ext.create("ShopinDesktop.GuideSupplyStatisticsWindow", {
											id: "guideSupplyStatisticsWindow1",
											title:"按供应商统计",
											baseUrl: _appctx,
											itemsPerPage: 20,
											width: "80%",
											region: "center",
											closable:true
										})
									  );
									  Ext.getCmp('statisticalView').setActiveTab(Ext.getCmp("guideSupplyStatisticsWindow1"));
									  //add by qutengfei for 操作日志查询（supply_guide_log） in 20150714 begin
									  /**
										* 导购手动变价权限日志报表
										* for demand
										* feature https://tower.im/s/beCeH
										* author qutengfei
										*/
								  }else if(newId=="operaLogManagementWindow"){
									  Ext.getCmp('statisticalView').add(
										Ext.create("ShopinDesktop.OperaLogManagementWindow", {
											id: "operaLogManagementWindow1",
											title:"操作日志查询",
											baseUrl: _appctx,
											itemsPerPage: 20,
											width: "80%",
											region: "center",
											closable:true
										})
									  );
									  Ext.getCmp('statisticalView').setActiveTab(Ext.getCmp("operaLogManagementWindow1"));
								  }
 									 //add by qutengfei for 操作日志查询（supply_guide_log） in 20150714 end
//								 }else{
//									Ext.MessageBox.alert("提示","您没有此权限");
//								 }

							 }else{
								 Ext.getCmp('statisticalView').setActiveTab(oldId);
							 }
						  }
					  }
			       }),Ext.create("Ext.TabPanel",{
						id:"statisticalView",
						width: "80%",
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

