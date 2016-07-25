/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 * 12-25
 */

Ext.define('ShopinDesktop.SupplierViewWindow', {
	extend: 'Ext.ux.desktop.Module',
	requires: [
		'Ext.data.ArrayStore',
		'Ext.util.Format',
		'Ext.grid.Panel',
		'Ext.TabPanel',
		'Ext.grid.RowNumberer',
//		'Member.AdminInforView'
//		'Supply.AddSupplyBrandView',
//		'Supply.SupplyInforView'
//		'Member.TowDimensionView',
//		'Member.UpdateMessageWindow'
	],
	id:'supplierViewWindow',
	init : function(){
//		this.launcher = {
//			text: '供应商平台',
//			iconCls:'syn-icon'
//		};
	},

	createWindow : function(){
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('SupplierViewWindowid');
		
		
		importImagePath = "http://172.16.200.4/images";
		
		if(!win){
			win = desktop.createWindow({
				id: 'SupplierViewWindowid',
				title:'供应商管理',
				width:'95%',
				height:'95%',
				iconCls: 'syn-icon',
				animCollapse:false,
				constrainHeader:true,
				layout: 'border',
				items: [Ext.create("Ext.tree.TreePanel",{ 
					title : '导航管理',
			        region:'west',
			        split:true,
			        width: "15%",
			        collapsible:true,
			        rootVisible : false,
			        root:{
			    	  	expanded : true,
			    	  	children:[{
			        		text: "供应商管理",
							iconCls:'button_tree',
							leaf: true,
							id:"SupplyInforView",
						}]
			       },
					  listeners :{
						
						  itemclick:function(view, record,item){
							  var newId = record.data.id;
							  var oldId =Ext.getCmp(newId+1);
							  if (oldId == undefined) {
							
 									if(newId=="SupplyInforView"){
									  Ext.getCmp('SupplierViewWindowborder').add(
										   Ext.create("Supply.SupplyInforView", {
												id: "SupplyInforView1",
												title:"供应商管理",
												url : _appctx,
												//baseUrl: __ctxPath,
												itemsPerPage: 20,
												width: "80%",
												region: "center",
												closable:true
										   })
									  );
									  Ext.getCmp('SupplierViewWindowborder').setActiveTab(Ext.getCmp("SupplyInforView1"));
								  }else if(newId=="brandInforView"){
									   Ext.getCmp('SupplierViewWindowborder').add(
										   Ext.create("Supply.BrandLOGOView", {
												id: "brandInforLOGOView2",
												title:"品牌LOGO管理",
												//baseUrl: __ctxPath,
												url : _appctx,
												itemsPerPage: 20,
												width: "80%",
												region: "center",
												closable:true
										   })
									  );
									  Ext.getCmp('SupplierViewWindowborder').setActiveTab(Ext.getCmp("brandInforLOGOView2"));
								  }
								
							 }else{
								 Ext.getCmp('SupplierViewWindowborder').setActiveTab(oldId);
							 }
						  }
					  }
			       }),Ext.create("Ext.TabPanel",{
						id:"SupplierViewWindowborder",
						width: "70%",
						region:'center',//中间面板
						activeTab: 0,
						enableTabScroll : true,
						autoScroll : true,						
			 			frame:true, 
			 			items:[{}]
				})
				]
			});
		}
		return win;
	}
});

