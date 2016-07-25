Ext.define("ShopinDesktop.RoleUserWindow", {
	extend: "Ext.window.Window",
	requires: [
		'Ext.form.Panel',
    	'Ext.ux.form.MultiSelect',
    	'Ext.ux.form.ItemSelector',
    	'Ext.tip.QuickTipManager',
    	'Ext.ux.ajax.JsonSimlet',
    	'Ext.ux.ajax.SimManager'
	],
	isForm: null,
	msForm:null,
	id: "roleUserWindow",
	baseUrl: _appctx,
	record: null,
	icon: null,
	caller: null,
	roleSid : null,
	checkedUser:null,
	panel:null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			height: 350,
			width: 500,
			modal: true,
			constrain: true,
			layout: "border",
			items: [this.isForm],
			title: "角色关联用户信息"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var roleSid = this.roleSid;
	    var checkedUser = this.checkedUser;
	    var checkedUserArr = "";
	    if(checkedUser != undefined){
	   	 	checkedUserArr = checkedUser.split(",");
	    }
	    Ext.tip.QuickTipManager.init();
	
	    var ds = Ext.create('Ext.data.ArrayStore', {
	    	autoLoad: true,
	        fields: ['sid','userCode'],
	        proxy: {
	            type: 'ajax',
	            url:  _appctx + "/systemUser/getAllValidUser.json",
	            reader: 'array',
	            reader: {
					type: "json",
					root : "list",
					
				},
	        },
	        sortInfo: {
	            field: 'value',
	            direction: 'ASC'
	        }
	    });
		
	    this.isForm = Ext.widget('form', {
	        width: 498,
	        bodyPadding: 10,
	        height: 348,
	        layout: 'fit',
	        items:[{
	            xtype: 'itemselector',
	            name: 'itemselector',
	            id: 'itemselector-field',
	            anchor: '100%',
	            imagePath:'js/extjs/src/ux/css/images/',
	            store: ds,
	            displayField: 'userCode',
	            valueField: 'sid',
	            value: checkedUserArr,
	            allowBlank: false,
	            msgTarget: 'side',
	            fromTitle: '所有用户',
	            toTitle: 'Selected'
	        }],
			dockedItems:[{
	            xtype: 'toolbar',
	            dock: 'bottom',
	            ui: 'footer',
	            defaults: {
	                minWidth: 75
	            },
	            items: ['->'
	            	,{
	                text: '保存',
	                handler: function(){
	                    var userSid = Ext.getCmp('itemselector-field').getValue();
	                    var userSids = "";
	                    for(var i=0;i<userSid.length;i++){
	                    	userSids += userSid[i] + ",";
	                    }
	                    	Ext.Ajax.request({
							  url: _appctx + "/roleUser/savaRoleUser.json",
							   _method:'POST',
								params: { 
								
									_method : "POST",
									roleSid : roleSid,
									userSids: userSids
									
								},
				
							success: function(response, options) {
								var response = Ext.JSON.decode(response.responseText);
								if("true"== response.success){
									Ext.MessageBox.alert("提示", "保存成功", function() {
										Ext.getCmp("roleUserWindow").close();
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
	                }
	            },{
	            	 text: '关闭',
	                handler: function(){
	                 thisWindow.close();
	                }
	            }]
	        }]
	
	    });
	}
});