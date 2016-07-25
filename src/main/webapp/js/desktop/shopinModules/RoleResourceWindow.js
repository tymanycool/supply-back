Ext.define("ShopinDesktop.RoleResourceWindow", {
	extend: "Ext.window.Window",
	requires: [
		'Ext.tree.TreePanel'
	],
	panel: null,
	id: "roleResourceWindow",
	baseUrl: _appctx,
	record: null,
	icon: null,
	caller: null,
	roleSid : null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			height: 500,
			width: 300,
			modal: true,
			constrain: true,
			layout: "border",
			items: [this.panel],
			title: "角色关联资源信息"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var roleSid = this.roleSid;
		var treeStore = Ext.create("Ext.data.TreeStore",{
			autoSync : true,
			proxy: {
            type: 'ajax',
            url: _appctx + "/roleResources/getAllResources.json",
            extraParams:{   
               roleSid:roleSid 
            }  
        	},
        	sorters: [{
            	property: 'leaf',
            	direction: 'ASC'
        	}, {
            	property: 'text',
            	direction: 'ASC'
        	}],
		});
		this.panel = Ext.create("Ext.tree.TreePanel",{ 
					title : '资源树',
			        region:'west',
			        id:'resourceTree',
			        split:true,
			        collapsible:true,
			        autoScroll:true, 
			        store : treeStore,
			        root:{id:"0",text:'根资源',leaf:false ,expanded:true},
			        rootVisible : true,
			        buttons: [{
						text: "保存",
						id : "saveButton",
						handler: function(button, e) {
							thisWindow.saveEdit();
						}
					},{
						text: "取消",
						handler: function(button, e) {
							thisWindow.cancel();
						}
					}],
					listeners:{
						checkchange: function(node,checked,eOpts) {
			        		   if (checked == true) {
							    node.checked = checked;
							
							    //获得父节点
							    pNode = node.parentNode;
							
							  //当checked == true通过循环将所有父节点选中
							    for (; pNode != null; pNode = pNode.parentNode){
							     pNode.set("checked", true);
							    }
							   }
							
							  //当该节点有子节点时，将所有子节点选中删除
							   if (!node.get("leaf") && !checked)
							      node.cascade(function(node){
							     node.set('checked', false);
							     
							    });
                		}
					}
			       });
	},
	saveEdit: function() {
		var Tree = Ext.getCmp('resourceTree');
		
    	var rsSids = Tree.getChecked();
    	
    	var resourceSid = "";
    	for(var i=0;i<rsSids.length;i++){
    		resourceSid +=rsSids[i].data.id;
    		resourceSid+=",";
    	}
   
		Ext.Ajax.request({
			  url: _appctx + "/roleResources/savaRoleResources.json",
			   _method:'POST',
				params: { 
				
					_method : "POST",
					resourceSid:resourceSid,
					orleSid: this.roleSid
					
				},

			success: function(response, options) {
				var response = Ext.JSON.decode(response.responseText);
				if("true"== response.success){
					Ext.MessageBox.alert("提示", "保存成功", function() {
						Ext.getCmp("roleResourceWindow").close();
					});
				}else{
					Ext.MessageBox.alert("提示", "操作失败", function() {
					});
				}

			},
			failure: function(response, options) {
				Ext.MessageBox.alert("Error", "保存失败。");
			}
		});
	},
	cancel: function() {
		this.close();
	}
});