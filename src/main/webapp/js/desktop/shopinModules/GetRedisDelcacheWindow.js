/**
 * PAD信息管理-新
 * @param {Object} config
 * @memberOf {TypeName} 
 * @author SunYukun
 */
Ext.define('ShopinDesktop.GetRedisDelcacheWindow',{
	extend: 'Ext.ux.desktop.Module',
	requires : [
	            'Ext.data.ArrayStore',
	            'Ext.util.Format',
	            'Ext.grid.Panel',
	            'Ext.TabPanel',
	            'Ext.grid.RowNumberer',
	            'ShopinDesktop.selectRedisView',
	            'ShopinDesktop.delRedisView'
	      /*      'ShopinDesktop.PadPartsView'*/
	           ],
	id:'getRedisDelcacheWindow',
	init:function(){
		
	},
	createWindow:function(){
		//TODO 权限判断
		var arr = allResource.split(',');
		function hasPermission(permission){
			return Ext.Array.contains(arr,permission);
		}
		
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('GetRedisDelcacheWindow');
		var treeStore1 = Ext.create("Ext.data.TreeStore",{
			root:{
				cls : 'feeds-node',
				text : '缓存管理',
				expanded : true,
					children:[{
						text: "查询缓存数据",
						iconCls:'button_tree',
						leaf: true,
						id:"selectRedisView",
					},{
						text: "清除缓存数据",
						iconCls:'button_tree',
						leaf: true,
						id:"delRedisView"
					}]
			}
		});
		if(!win){
			win = desktop.createWindow({
				id:'GetRedisDelcacheWindow',
				title:'供应商缓存管理',
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
			        store : treeStore1,
			        rootVisible : false,  //所以pad管理看不到。
			        listeners:{
			        	itemclick:function(view, record,item){
			        		//TODO 权限限制到按钮
			        		var newId = record.data.id;
			        		var oldId = Ext.getCmp(newId+1);
			        		if(oldId == undefined){
			        			if(newId=="selectRedisView"){
//			        				if(!hasPermission("selectRedisView")){
//			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
//			        					return;
//			        				}
			        				Ext.getCmp('cacheInfoView').add(
			        						Ext.create("ShopinDesktop.selectRedisView", {
												id: 'selectRedisView1',
												title:"查询缓存数据",
												baseUrl: _appctx,
												itemsPerPage: 20,
												width: "90%",
												region: "center",
												closable:true
											})
										  );
			        				Ext.getCmp('cacheInfoView').setActiveTab(Ext.getCmp("selectRedisView1"));
			        			}else if(newId=="delRedisView"){
//			        				if(!hasPermission("delRedisView")){
//			        					Ext.Msg.alert('提示',"只有管理员才有权操作！");
//			        					return;
//			        				}
			        				Ext.getCmp('cacheInfoView').add(
											Ext.create("ShopinDesktop.delRedisView", {
												id: "delRedisView1",
												title:"清除缓存数据",
												baseUrl: _appctx,
												itemsPerPage: 20,
												width: "90%",
												region: "center",
												closable:true
											})
										  );
			        				Ext.getCmp('cacheInfoView').setActiveTab(Ext.getCmp("delRedisView1"));
			        			}
			        		}else{
								 Ext.getCmp('cacheInfoView').setActiveTab(oldId);
							}
			        	}
			        }
				}),Ext.create("Ext.TabPanel",{
					id:"cacheInfoView",
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