Ext.define("ShopinDesktop.UpdateUserInforWindow", {
	extend: "Ext.window.Window",
	panel: null,
	id: "updateUserInforWindow",
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
			height: 320,
			width: 300,
			modal: true,
			constrain: true,
			layout: "border",
			items: [this.panel],
			title: "用户信息修改"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var record = this.record;
		
		var validBitCombo= new Ext.data.ArrayStore ({
			
			fields: ['validBitCode','value'],
			data : [
				['0',"无效"],['1',"有效"]
			]
		});
		
		var shopCombo= new Ext.data.JsonStore({
			autoLoad:true,
			proxy : {
				type : 'ajax',
				api : {
					read : _appctx + '/guideSupply/getShopList',
				},
				idParam : 'sid',
				reader : {
					type : 'json',
					root : 'result'
				}
			},
			fields: ["sid", "shopName"]
		});
		
		this.panel = Ext.create("Ext.form.Panel", {
			bodyPadding: "25 15 25 15",
			id:'memberInforPanel',
			buttonAlign: "center",
			buttons: [{
				text: "保存",
				handler: function(button, e) {
					thisWindow.saveEdit();
				}
			},{
				text: "取消",
				handler: function(button, e) {
					thisWindow.cancel();
				}
			}],
			defaultType: "textfield",
			items: [{
				xtype: "hiddenfield",
				name: "sid",
				value: record.data.sid
			},{
				xtype: "textfield",
				fieldLabel: "登录名",
				name: "userCode",
				value: record.data.userCode
			},{
				inputType:'password', 
				fieldLabel: "密码",
				name: "userPassword",
				value: "111111"
			},{
				xtype: "textfield",
				fieldLabel: "真实姓名",
				name: "userName",
				value: record.data.username
			},
			{	
				xtype:"combo",
				fieldLabel : '用户所属门店',
				editable:false,
				id:"updateShopinfo",
				store: shopCombo,
				queryMode: "local",
				valueField: 'sid',
				displayField:'shopName',
				hiddenName:'sid',
				triggerAction : 'all',
				name:'shop',
				mode:'local'
			},
			{
				xtype: "combo",
				fieldLabel: "是否有效",
				editable:false,
				id:"userValidBit",
				store: validBitCombo,
				queryMode: "local",
				valueField: "validBitCode",
				displayField: "value",
				hiddenName:'validBitCode',
				triggerAction : 'all',
				name: "validBit",
				mode:'local'
			}],
			
			region: "center"
		});
		
		this.panel.form.findField('userValidBit').select(validBitCombo.getAt(record.data.validBit));
		this.panel.form.findField('updateShopinfo').select(shopCombo.getAt(record.data.shopSid));
		
		shopCombo.on('load', function (){
    		 Ext.getCmp('updateShopinfo').setValue(record.data.shopSid);    
		});
			
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var editForm = this.panel.getForm();
		
		var shopName = Ext.getCmp("updateShopinfo").getRawValue();
		var shopSid = Ext.getCmp("updateShopinfo").getValue();
		
		if (editForm.isValid()) {
			editForm.submit({
				url: _appctx + "/systemUser/updateUser.json",
				params:{
					shopName :shopName,
					shopSid : shopSid
				},
				success: function(form, action) {
					Ext.MessageBox.alert("Success", "保存成功", function() {
						Ext.getCmp('UserInforView1').getStore().load();
						thisWindow.close();
						caller.pageBar.doRefresh();
					});
				},
				failure: function(form, action) {
					switch (action.failureType) {
						case Ext.form.action.Action.CONNECT_FAILURE:
							Ext.MessageBox.alert("Error", "保存失败。请检查网络连接或确认服务器是否运行");
							break;
						case Ext.form.action.Action.SERVER_INVALID:
							var response = Ext.JSON.decode(action.response.responseText);
							Ext.MessageBox.alert("Error", "保存失败。" + response.errorMsg);
							break;
						case Ext.form.action.Action.LOAD_FAILURE:
							Ext.MessageBox.alert("Error", "保存失败。远程服务器返回了不能识别的数据格式，请联系管理员");
					}
				}
			});
		}
	},
	cancel: function() {
		this.close();
	}
});