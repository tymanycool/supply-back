Ext.define("ShopinDesktop.UpdatePadInfoWindow", {
	extend: "Ext.window.Window",
	panel: null,
	id: "updatePadInfoWindow",
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
			title: "PAD进货信息修改"
		});
	},
	initComponents: function() {
		var thisWindow = this;
		var record = this.record;
		
		this.panel = Ext.create("Ext.form.Panel", {
			bodyPadding: "25 15 25 15",
			id:'padInfoPanel',
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
				fieldLabel: "批次号",
				name: "padPurchaseBatchNo",
				allowBlank:false,
				value: record.data.padPurchaseBatchNo
			},{
				inputType:'textfield', 
				fieldLabel: "PAD品牌",
				allowBlank:false,
				name: "padBrand",
				value: record.data.padBrand
			},{
				xtype: "textfield",
				fieldLabel: "PAD厂商",
				allowBlank:false,
				name: "padSupply",
				value: record.data.padSupply
			},{
				xtype: "datefield",
				fieldLabel: "PAD订货时间",
				allowBlank:false,
				format: 'Y-m-d',
				name: "padPurchaseTime",
				value: record.data.padPurchaseTime.substring(0,10)
			},{
				xtype: "numberfield",
				fieldLabel: "PAD订货数量",
				allowNegative:false,
				allowBlank:false,
				name: "padPurchaseNum",
				value: record.data.padPurchaseNum
			}
			],
			region: "center"
		});
		
	},
	saveEdit: function() {
		var thisWindow = this;
		var caller = this.caller;
		var editForm = this.panel.getForm();
		if (editForm.isValid()) {
			editForm.submit({
				url: _appctx + "/padPurchaseInfo/updatePadPurchaseInfo.json",
				success: function(form, action) {
					if(action.result.success == "true"){
						Ext.MessageBox.alert("Success", "保存成功", function() {
							Ext.getCmp('PadPurchaseView1').getStore().load();
							thisWindow.close();
							caller.pageBar.doRefresh();
						});
					}else if(action.result.success == "false"){
						if(action.result.errorCode == "10000"){
							Ext.Msg.alert('提示',action.result.memo);
						}else if(action.result.errorCode == "00000"){
							Ext.Msg.alert('提示',action.result.memo);
						}
						else{
							Ext.Msg.alert('提示',"pad信息添加失败！");
						}
					}
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