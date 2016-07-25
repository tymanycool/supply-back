/**
 * 用户权限管理
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.UserAndAuthViewWindow', {
	extend: 'Ext.ux.desktop.Module',
	requires: [
		'Ext.data.ArrayStore',
		'Ext.util.Format',
		'Ext.grid.Panel',
		'Ext.TabPanel',
		'Ext.grid.RowNumberer',
		'ShopinDesktop.AdminInforView'
	],
	id:'userAndAuthViewWindow',
	init : function(){
//		this.launcher = {
//			text: '权限管理',
//			iconCls:'brand-icon'
//		};
	},

	createWindow : function(){
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('userAndAuthViewWindow');
		var treeStore = Ext.create("Ext.data.TreeStore",{
			root:{
				cls : 'feeds-node',
				text : '用户权限管理',
				expanded : true,
				children:[{
					text: "用户管理",
					iconCls:'button_tree',
					leaf: true,
					id:"UserInforView",
				},{
					text: "角色管理",
					iconCls:'button_tree',
					leaf: true,
					id:"AdminInforView"
				},{
					text: "资源管理",
					iconCls:'button_tree',
					leaf: true,
					id:"ResourceViewWindow"
				}
				]
			},
		});
		if(!win){
			win = desktop.createWindow({
				id: 'userAndAuthViewWindow',
				title:'用户和权限管理',
				width:940,
				height:480,
				iconCls: 'brand-icon',
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
 									if(newId=="UserInforView"){
									  Ext.getCmp('userAndAuthView').add(
										Ext.create("ShopinDesktop.UserInforView", {
											id: "UserInforView1",
											title:"用户信息",
											baseUrl: _appctx,
											itemsPerPage: 20,
											width: "80%",
											region: "center",
											closable:true
										})
									  );
									  Ext.getCmp('userAndAuthView').setActiveTab(Ext.getCmp("UserInforView1"));
								  }else if(newId=="AdminInforView"){
									  Ext.getCmp('userAndAuthView').add(
										Ext.create("ShopinDesktop.AdminInforView", {
											id: "AdminInforView1",
											title:"角色信息",
											baseUrl: _appctx,
											itemsPerPage: 20,
											width: "80%",
											region: "center",
											closable:true
										})
									  );
									  Ext.getCmp('userAndAuthView').setActiveTab(Ext.getCmp("AdminInforView1"));
								  }else if(newId == 'ResourceViewWindow'){
									  Ext.create("ShopinDesktop.ResourceViewWindow", {
												id: "ResourceViewWindow1",
												baseUrl: this.baseUrl,
												caller: this
									  }).show();
								  }
// 									else if(newId=="AdminInforView"){
//									  Ext.getCmp('userAndAuthView').add(
//										Ext.create("ShopinDesktop.AdminInforView", {
//											id: "AdminInforView1",
//											title:"角色信息",
//											baseUrl: _appctx,
//											itemsPerPage: 20,
//											width: "80%",
//											region: "center",
//											closable:true
//										})
//									  );
//									  Ext.getCmp('userAndAuthView').setActiveTab(Ext.getCmp("AdminInforView1"));
//								  }
							 }else{
								 Ext.getCmp('userAndAuthView').setActiveTab(oldId);
							 }
						  }
					  }
			       }),Ext.create("Ext.TabPanel",{
						id:"userAndAuthView",
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

