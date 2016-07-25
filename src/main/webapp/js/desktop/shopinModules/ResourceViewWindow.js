Ext.define("ShopinDesktop.ResourceViewWindow", {
	extend: "Ext.window.Window",
	panel: null,
	treePanel : null,
	id: "ResourceViewWindow1",
	baseUrl: "",
	record: null,
	icon: null,
	caller: null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			width:740,
			height:480,
			modal: true,
			constrain: true,
			layout: "border",
			items: [this.treePanel,this.panel],
			title: "资源管理"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var record = this.record;
		var treeStore = Ext.create("Ext.data.TreeStore",{
			proxy: {
            type: 'ajax',
            url: _appctx + "/resources/getAllResources.json"
        	},
        	sorters: [{
            	property: 'leaf',
            	direction: 'ASC'
        	}, {
            	property: 'text',
            	direction: 'ASC'
        	}]
		});
		var resourceStore = Ext.create("Ext.data.Store",{
			autoLoad:false,
			pageSize : this.itemsPerPage,
			fields : [
				  "sid","rsName","rsCode","createTime","updateTime","delFlag","parentSid","isLeaf","checked"
					],
 			proxy: {
						type: "ajax",
						url : _appctx + "/resources/getResourceByParam.json",
						getMethod: function(){
							return 'POST';
						},
						reader: {
							type: "json",
							root : "list"
						},
						listeners: {
							exception: function(proxy, response, operation, eOpts) {
								if (response.status != 200) {
									Ext.MessageBox.alert("Error", "加载列表失败！");
								}
							}
						}
				},
				listeners: {
					beforeload : function(store, operation, eOpts) {
//						console.log(operation);
//						this.getProxy().setExtraParam("rsName",
//							Ext.getCmp("resourceNames").getValue());
					}
				}
		});
		var resourceColumns=[
						new Ext.grid.RowNumberer({
							width:40
						}),
						{
							header : "资源SID",
							dataIndex : "sid",
							width:80

						},
			         	{
							header : "资源名称",
							dataIndex : "rsName",
							width:80

						},
						{
							header : "资源编码",
							dataIndex : "rsCode",
							width:80

						}
						,{
							header : "创建时间",
							dataIndex : "createTime",
							width:150

						}
						,{
							header : "修改时间",
							dataIndex : "updateTime",
							width:150

						}
						,{
							header: "是否有效",
							dataIndex: "delFlag",
							renderer:function(v){
								if(v == '0'){
									return "有效";
								}else{
									return "无效";
								}
							},
							width:60

						},{
							header: "父节点SID",
							dataIndex: "parentSid",
							width:70
						},{
							header: "是否是叶子节点",
							dataIndex: "isLeaf",
							renderer:function(v){
								if(v == '0'){
									return "否";
								}else{
									return "是";
								}
							},
							width:60

						}
		];

		var treeNode = null;
		var sellAction = Ext.create('Ext.Action', {
        //	icon   : '../shared/icons/fam/delete.gif',  // Use a URL in the icon config
        	text: '添加资源',
        	disabled: false,
        	handler: function(widget, event) {
				Ext.create("ShopinDesktop.AddResourceInforWindow", {
							id: "addResourceInforWindow",
							baseUrl: this.baseUrl,
							caller: this,
							sid : treeNode.id
				}).show();
        	}
    	});
		var buyAction = Ext.create('Ext.Action', {
        	//iconCls: '../js/extjs/resources/themes/images/default/dd/drop-no.gif',
        	text: '删除资源',
        	disabled: false,
        	handler: function(widget, event) {
			var record = treeStore.getNodeById(treeNode.id);
 //           var treeNode = Ext.getCmp("navTree").getSelectionModel().select(record)

       		if(treeNode.leaf){
				deleteNode(treeNode);
			}else{
				if(treeNode.expanded ){
					if(record.childNodes.length<=0){
						deleteNode(treeNode);
					}else{
						Ext.MessageBox.alert("错误信息","当前节点存在子节点不能删除");
					}
				}else{
					Ext.MessageBox.alert("提示信息","请展开当前节点查看是否存在子节点");
				}

			}
        	}
    	});
		var forbiddenAction = Ext.create('Ext.Action', {
        	//iconCls: '../js/extjs/resources/themes/images/default/dd/drop-no.gif',
        	text: '禁用资源',
        	disabled: false,
        	handler: function(widget, event) {

			 if(treeNode == null){
				Ext.Msg.alert("","请选择要禁用的资源");
			}else{

				Ext.Msg.show({
					title:'提示消息',
					icon: Ext.MessageBox.QUESTION,
					msg:'确认禁用?',
					buttons: Ext.MessageBox.YESNO,
					fn:function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({

							   url: _appctx + "/resources/disableResources.json",
							   _method:'POST',
							   params: {

									_method : "POST",
									sid: treeNode.id

								   },
							   success: function(){
							      Ext.Msg.alert('信息提示','禁用成功');
							      resourceStore.load({
									params:{
										sid : treeNode.id
									}
								});
							     },
							     failure: function(){
							          Ext.Msg.alert('信息提示','禁用失败，请与管理员联系');
				             	}

				         	});
						}
					}
				});

			}
        	}
    	});
		var contextMenu = Ext.create('Ext.menu.Menu', {
        	items: [
        		sellAction,
            	buyAction,
            	forbiddenAction
        	]
    	});
		function deleteNode(treeNode){

				Ext.Msg.show({
					title:'提示消息',
					icon: Ext.MessageBox.QUESTION,
					msg:'确认删除?',
					buttons: Ext.MessageBox.YESNO,
					fn:function(btn){
						if(btn=='yes'){
						//	treeNode.remove();
							Ext.Ajax.request({
							   url: _appctx + "/resources/deleteResources.json",
							   _method:'POST',
							   params: {

									_method : "POST",
									sid : treeNode.id
								   },
							   success: function(response, options){
									      Ext.Msg.alert('信息提示','删除成功');
									      treeStore.load();
									      resourceStore.load();
								},

						      failure: function(){
							          Ext.Msg.alert('信息提示','删除失败，请与管理员联系');
				             	}

				         	});
						}
						}

				});
		}

		this.treePanel = Ext.create("Ext.tree.TreePanel",{
					title : '导航管理',
			        region:'west',
			        id:'navTree',
			        split:true,
			        width: "20%",
			        collapsible:true,
			        store : treeStore,
			        root:{id:"0",text:'根资源',leaf:false ,expanded:true},
			        rootVisible : true,
					listeners:{
						itemclick:function(view, record,item){
							var navSid = record.data.id;
							resourceStore.load({
								params:{
									sid : navSid
								}
							});
						},
						itemcontextmenu: function(view, rec, node, index, e) {
								treeNode= rec.data;
                    			e.stopEvent();
                    			contextMenu.showAt(e.getXY());
                    			return false;
                		}
					}
			       });
		this.panel =Ext.create("Ext.grid.Panel",{
						id:"resourceView",
						width: "80%",
						region:'center',//中间面板
						activeTab: 0,
						enableTabScroll : true,
						autoScroll : true,
			 			frame:true,
			 			columns: resourceColumns,
//						tbar: resourceTbar,
						store: resourceStore,
	 					listeners:{
							itemdblclick:function(view, record,item){
								if(record !=null){
									Ext.create("ShopinDesktop.UpdateResourceInforWindow", {
										id: "updateResourceInforWindow",
										baseUrl: this.baseUrl,
										caller: this,
										record: record

									}).show();
								}else{
									Ext.MessageBox.alert("提示","请选择一条记录");
								}

		}
						}

				});
	}
});