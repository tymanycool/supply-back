Ext.define('ShopinDesktop.PadAndGuideManageWindow', {
	extend : 'Ext.ux.desktop.Module',
	requires: [
		'Ext.data.ArrayStore',
		'Ext.util.Format',
		'Ext.grid.Panel',
		'Ext.TabPanel',
		'Ext.grid.RowNumberer',
		'ShopinDesktop.PadBaseinfoManageWindow',
		'ShopinDesktop.GuideInfoManageWindow'
	],
	id: 'padAndGuideManageWindow', 
	
	createWindow : function(){
		var me = this;
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if(!win){
			win = desktop.createWindow({
				id: me.id,
				title: me.title,
				width : 1300,
				height : 580,
				iconCls: me.launcher.iconCls,
				animCollapse:false,
				constrainHeader:true,
				layout: 'border',
				items: [Ext.create("Ext.tree.TreePanel",{ 
					title : '导航',
			        region:'west',
			        split:true,
			        width: "18%",
			        collapsible:true,
			        rootVisible : true,
			       root:{
			    	   cls : 'feeds-node',
						text : 'PAD与导购信息管理',
						expanded : true,
						children:[{
							  text: "PAD管理", 
							  iconCls:'button_tree',
							  id:"padManageViewId",
							  leaf: true,
						},{
							text: "导购管理", 
							  iconCls:'button_tree',
							  id:"guideManageViewId",
							  leaf: true,
						}]
			       },
					  listeners:{
						  itemclick:function(view, record,item){
							  var newId = record.data.id;
							  var newText = record.data.text;
							  var oldId =Ext.getCmp(newId+1);
							  if (oldId == undefined) {
								  if(newId=="padManageViewId"){
									  Ext.getCmp('ShopinPadAndGuideView').add(
											  Ext.create("ShopinDesktop.PadBaseinfoManageWindow", {
													id: "padManageViewId1",
													title:"PAD管理",
													baseUrl: _appctx,
													itemsPerPage: 20,
													width: "80%",
													region: "center",
													closable:true
												})
									  );
									  Ext.getCmp('ShopinPadAndGuideView').setActiveTab(Ext.getCmp("padManageViewId1"));
								  }else if(newId=="guideManageViewId"){
									  Ext.getCmp('ShopinPadAndGuideView').add(
												 Ext.create("ShopinDesktop.GuideInfoManageWindow", {
												id: "guideManageViewId1",
												title:"导购管理",
												baseUrl: _appctx,
												itemsPerPage: 20,
												width: "80%",
												region: "center",
												closable:true
											})
										  );
										  Ext.getCmp('ShopinPadAndGuideView').setActiveTab(Ext.getCmp("guideManageViewId1"));
									  }
							 }else{
								 Ext.getCmp('ShopinPadAndGuideView').setActiveTab(oldId);
							 }
						  }
					  }
			       }),Ext.create("Ext.TabPanel",{
						id:"ShopinPadAndGuideView",
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
