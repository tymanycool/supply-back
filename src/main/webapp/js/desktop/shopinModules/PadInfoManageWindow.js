/**
 * PAD信息管理-新
 * @param {Object} config
 * @memberOf {TypeName} 
 * @author SunYukun
 */
Ext.define('ShopinDesktop.PadInfoManageWindow',{
	extend: 'Ext.ux.desktop.Module',
	requires : [
	            'Ext.data.ArrayStore',
	            'Ext.util.Format',
	            'Ext.grid.Panel',
	            'Ext.TabPanel',
	            'Ext.grid.RowNumberer',
	            'ShopinDesktop.PadPurchaseView',
	            'ShopinDesktop.PadInfoCheckView',
	            'ShopinDesktop.PadPartsView'
	           ],
	id:'padInfoManageWindow',
	init:function(){
		
	},
	createWindow:function(){
		//TODO 权限判断
		var arr = allResource.split(',');
		function hasPermission(permission){
			return Ext.Array.contains(arr,permission);
		}
		
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('padInfoManageWindow');
		var treeStore = Ext.create("Ext.data.TreeStore",{
			root:{
				cls : 'feeds-node',
				text : 'PAD管理',
				expanded : true,
					children:[{
						text: "PAD入库管理",
						iconCls:'button_tree',
						leaf: true,
						id:"PadPurchaseView",
					},{
						text: "PAD信息录入",
						iconCls:'button_tree',
						leaf: true,
						id:"PadInfoInputView"
					},{
						text: "PAD信息查看",
						iconCls:'button_tree',
						leaf: true,
						id:"PadInfoCheckView"
					},{
						text: "PAD调拨管理",
						iconCls:'button_tree',
						leaf: true,
						id:"PadAllocateView"
					},{
						text: "PAD信息报表",
						iconCls:'button_tree',
						leaf: true,
						id:"PadInfoReportView"
					}/*,{
						text:"PAD配件管理",
						iconCls:"button-tree",
						leaf:"true",
						id:"PadPartsView",
					}*/
					]
			}
		});
		if(!win){
			win = desktop.createWindow({
				id:'padInfoManageWindow',
				title:'PAD管理',
				width:1350,
				height:600,
				iconCls: 'brand-icon',
				animCollapse:false,
				constrainHeader:true,
				layout: 'border',
				items: [Ext.create("Ext.tree.TreePanel",{
					title : '导航管理',
			        region:'west',
			        split:true,
			        width: "10%",
			        collapsible:true,
			        store : treeStore,
			        rootVisible : false,  //所以pad管理看不到。
			        listeners:{
			        	itemclick:function(view, record,item){
			        		//TODO 权限限制到按钮
			        		var newId = record.data.id;
			        		var oldId = Ext.getCmp(newId+1);
			        		if(oldId == undefined){
			        			if(newId=="PadPurchaseView"){
			        				if(!hasPermission("PadPurchaseView")){
			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
			        					return;
			        				}
			        				Ext.getCmp('padInfoView').add(
			        						Ext.create("ShopinDesktop.PadPurchaseView", {
												id: 'PadPurchaseView1',
												title:"PAD入库管理",
												baseUrl: _appctx,
												itemsPerPage: 20,
												width: "90%",
												region: "center",
												closable:true
											})
										  );
			        				Ext.getCmp('padInfoView').setActiveTab(Ext.getCmp("PadPurchaseView1"));
			        			}else if(newId=="PadInfoInputView"){
			        				if(!hasPermission("PadInfoInputView")){
			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
			        					return;
			        				}
			        				Ext.getCmp('padInfoView').add(
											Ext.create("ShopinDesktop.PadInfoInputView", {
												id: "PadInfoInputView1",
												title:"PAD信息录入",
												baseUrl: _appctx,
												itemsPerPage: 20,
												width: "90%",
												region: "center",
												closable:true
											})
										  );
			        				Ext.getCmp('padInfoView').setActiveTab(Ext.getCmp("PadInfoInputView1"));
			        			}else if(newId=="PadInfoCheckView"){
			        				if(!hasPermission("PadInfoCheckView")){
			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
			        					return;
			        				}
			        				Ext.create("ShopinDesktop.PadInfoCheckView", {
										id: "PadInfoCheckView1",
										baseUrl: _appctx,
										title : 'PAD基本信息管理'
			        				}).show();
			        			}else if(newId=="PadAllocateView"){
			        				if(!hasPermission("PadAllocateView")){
			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
			        					return;
			        				}
			        				Ext.getCmp('padInfoView').add(
											Ext.create("ShopinDesktop.PadAllocateView", {
												id: "PadAllocateView1",
												title:"PAD调拨管理",
												baseUrl: _appctx,
												width: "90%",
												region: "center",
												closable:true
											})
										  );
			        				Ext.getCmp('padInfoView').setActiveTab(Ext.getCmp("PadAllocateView1"));
			        			}else if(newId=="PadInfoReportView"){
			        				if(!hasPermission("PadInfoReportView")){
			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
			        					return;
			        				}
			        				Ext.getCmp('padInfoView').add(
											Ext.create("ShopinDesktop.PadInfoReportView", {
												id: "PadInfoReportView1",
												title:"PAD信息报表",
												baseUrl: _appctx,
												width: "90%",
												region: "center",
												closable:true   //能不能关闭！！
											})
										  );
			        				Ext.getCmp('padInfoView').setActiveTab(Ext.getCmp("PadInfoReportView1"));
			        			}
			        		}else{
								 Ext.getCmp('padInfoView').setActiveTab(oldId);
							}
			        	}
			        }
				}),Ext.create("Ext.TabPanel",{
					id:"padInfoView",
					width: "80%",
					region:'center',
					activeTab: 0,
					enableTabScroll : true,   //允许Tab溢出时可以滚动，默认为false
					autoScroll : true,						
		 			frame:true, 
		 			items:[{}
		 			]
			})]
			});
		}
		return win;
	}
});