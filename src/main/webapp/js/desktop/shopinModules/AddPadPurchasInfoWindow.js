Ext.define("ShopinDesktop.AddPadPurchasInfoWindow", {
	extend : "Ext.window.Window",
	panel : null,
	id : "addPadPurchasInfoWindow",
	operator:username,
	baseUrl : "",
	record : null,
	icon : null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			height : 320,
			width : 350,
			modal : true,
			constrain : true,
			layout : "border",
			items : [ this.panel ],
			title : "PAD进货信息修改"
		});
	},
	initComponents : function() {
		var thisWindow = this;

		this.panel = Ext.create("Ext.form.Panel", {
			bodyPadding : "25 10 25 10",
			id : 'padInfoPanel',
			fieldDefaults : {
				labelAlign : 'left',
				labelWidth : 100,
				width : 300
			},
			buttonAlign : "center",
			buttons : [ {
				text : "保存",
				handler : function(button, e) {
					thisWindow.saveEdit();
				}
			}, {
				text : "取消",
				handler : function(button, e) {
					thisWindow.cancel();
				}
			} ],
			defaultType : "textfield",
			items : [ {
				xtype : "hidden",
				name:"padOperator",
				value:thisWindow.operator,
			}, {
				xtype : "textfield",
				fieldLabel : "批次号",
				name : "padPurchaseBatchNo",
				allowBlank : false,
				emptyText : '输入8位数字，格式为：20160101',
				regex : /^[0-9]{8}$/,
				regexText : '输入8位数字，格式为：20160101'
			}, {
				xtype : 'textfield',
				fieldLabel : "PAD品牌",
				allowBlank : false,
				name : "padBrand",
			}, {
				xtype : "textfield",
				fieldLabel : "PAD厂商",
				allowBlank : false,
				name : "padSupply",
			}, {
				xtype : "datefield",
				fieldLabel : "PAD订货时间",
				allowBlank : false,
				format : 'Y-m-d',
				name : "padPurchaseTime",
			}, {
				xtype : "numberfield",
				fieldLabel : "PAD订货数量",
				allowNegative : false,
				allowBlank : false,
				name : "padPurchaseNum",
				regex:/^[1-9]\d*$/     // >=1的整数
			}],
			region : "center"
		});

	},
	saveEdit : function() {
		var thisWindow = this;
		var editForm = this.panel.getForm();
		if (editForm.isValid()) {
			editForm
					.submit({
						url : _appctx
								+ "/padPurchaseInfo/addPadPurchaseInfo.json",
						success : function(form, action) {
							if (action.result.success == "true") {
								Ext.MessageBox.alert("Success", "保存成功",
										function() {
											Ext.getCmp('PadPurchaseView1')
													.getStore().load();
											thisWindow.close();
										});
							} else if (action.result.success == "false") {
								if (action.result.errorCode == "10000") {
									Ext.Msg.alert('提示', action.result.memo);
								} else if (action.result.errorCode == "00000") {
									Ext.Msg.alert('提示', action.result.memo);
								} else {
									Ext.Msg.alert('提示', "pad信息添加失败！");
								}
							}
						},
						failure : function(form, action) {
							switch (action.failureType) {
							case Ext.form.action.Action.CONNECT_FAILURE:
								Ext.MessageBox.alert("Error",
										"保存失败。请检查网络连接或确认服务器是否运行");
								break;
							case Ext.form.action.Action.SERVER_INVALID:
								var response = Ext.JSON
										.decode(action.response.responseText);
								Ext.MessageBox.alert("Error", "保存失败。"
										+ response.errorMsg);
								break;
							case Ext.form.action.Action.LOAD_FAILURE:
								Ext.MessageBox.alert("Error",
										"保存失败。远程服务器返回了不能识别的数据格式，请联系管理员");
							}
						}
					});
		}
	},
	cancel : function() {
		this.close();
	}
});