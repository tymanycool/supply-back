/**
 * 修改PAD基本信息
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadBaseinfoUpdateWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	sid :null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		var me = this;

		this.superclass.constructor.call(this, {
			title :'修改PAD基本信息',
			height : 310,
			id:'padBaseinfoUpdateId',
			width  : 420,
			constrain : true,
			layout:"fit",
			modal: true,
			maximizable:true,
			plain : true,
			items : [ this.formPanel],
			buttons :[
				{
					text : '保存',
					handler : function() {
						me.update();
					}
				},{
					text : '取消',
					handler : function() {
						me.cancel();
					}
				} 
			]
		});
	},
	initComponents : function() {
		
		var record = this.record;
		var sid = record.data.sid;
		var padNo = record.data.padNo;
		var macAddress = record.data.macAddress;
		var purchaseOrderno = record.data.purchaseOrderno;
		var padStatus = record.data.padStatus;
		var useType = record.data.useType;
		var brand = record.data.brand;
		
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		
		
//		var brandStr = Ext.getCmp('brandId');
//		var radioBrand = null;
//		if(brand == "第一代"){
//			radioBrand = new Ext.form.Radio(
//			{boxLabel: '第一代', name: 'brandName', inputValue: 10,checked: true});
//			falg = true;
//		}else{
//			radioBrand = new Ext.form.Radio(
//			{boxLabel: '第一代', name: 'brandName', inputValue: 10});
//		}
//		
//		brandStr.items.add(radioBrand);
//		if(brand == "第二代"){
//			radioBrand = new Ext.form.Radio(
//			{boxLabel: '第二代', name: 'brandName', inputValue: 0,checked: true});
//		}else{
//			radioBrand = new Ext.form.Radio(
//			{boxLabel: '第二代', name: 'brandName', inputValue: 0});
//		}
//		brandStr.items.add(radioBrand);
//		if(brand == "第三代"){
//			radioBrand = new Ext.form.Radio(
//					{boxLabel: '第三代', name: 'brandName', inputValue: 1,checked: true});
//			falg = true;
//		}else if(l==(strArrMember.length-1)&&!flag){
//			radioBrand = new Ext.form.Radio(
//					{boxLabel: '第三代', name: 'brandName', inputValue: 1});
//		}
//		brandStr.items.add(radioBrand);
		
		
		var padStatusUp = new Ext.data.ArrayStore({
			fields: ['padStatusCode','value'],
			data : [
			['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"]
			]
		});
		
		var useTypeCombo = new Ext.data.ArrayStore({
			fields: ['useTypeCode','value'],
			data : [
			        ['0',"导购"],['1',"主管"],['2',"内衣功能区"],['3',"大场"]
			        ]
		});
		
		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/padBaseinfo/save',
				layout : 'anchor',
				defaults : {
					anchor : '100%',
					labelAlign : 'right'
				},
				height : 310,
				id:"editForm",
				bodyPadding: "15 15 15 15",
				items: [
	                	{
	                    xtype: 'container',
	                    layout: 'hbox',
	                    items: [
	                    	{
	                    		xtype : 'textfield',
								name : 'sid',
								fieldLabel : 'SID',
								hidden:true,
								value: sid
							},
							{
                    			xtype : 'textfield',
								name : 'username',
								hidden:true,
								value : username
							},
							{
	                    		xtype : 'textfield',
								name : 'userSid',
								value : userSid,
								hidden:true
							},
	                    	{
                    			xtype: 'textfield',
								name : 'padNo',
								width : 300,
								fieldLabel : 'PAD编号',
								value : padNo,
								afterLabelTextTpl: required,
								allowBlank : false
							}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										xtype: 'textfield',
										name : 'macAddress',
										id : 'macAddress',
										width : 300,
										fieldLabel : 'MAC地址',
										afterLabelTextTpl: required,
										allowBlank : false,
										value : macAddress
									}
				                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
										xtype: 'textfield',
										name : 'purchaseOrderno',
										width : 300,
										fieldLabel : '采购订单号',
										value : purchaseOrderno
									}
			                    ]
			                }
			                ,{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"状态",
										xtype:"combo",
										width : 300,
										id:"status",
										store : padStatusUp,
										valueField: 'padStatusCode',
										displayField:'value',
										hiddenName:'padStatusCode',
										triggerAction : 'all',
										name:'padStatus',
										editable:false,
										mode:'local',
										afterLabelTextTpl: required,
										allowBlank : false
									}
			                    ]
			                }
			                ,{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"使用类型",
										xtype:"combo",
										width : 300,
										id:"useType",
										store : useTypeCombo,
										valueField: 'useTypeCode',
										displayField:'value',
										hiddenName:'useTypeCode',
										triggerAction : 'all',
										name:'useType',
										mode:'local',
										afterLabelTextTpl: required,
										editable:false,
										allowBlank : false
									},
									{
		                    			xtype : 'textfield',
										name : 'useTypeDesc',
										fieldLabel : 'useTypeDesc',
										hidden:true
									},
			                    ]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"品牌",
										xtype: 'radiogroup',
										id:'brandId',
										name:'brand',
										items: [
						                        { boxLabel: "第一代", name: "brandName", id:"brandId1",inputValue: "第一代" },
						                        { boxLabel: "第二代", name: "brandName", id:"brandId2",inputValue: "第二代" },
						                        { boxLabel: "第三代", name: "brandName", id:"brandId3",inputValue: "第三代" }
						                    ]
									},
									{
		                    			xtype : 'textfield',
										name : 'brandName',
										fieldLabel : 'brandName',
										hidden:true
									}
			                    ]
			                }
						]
				});
		
		this.formPanel.form.findField('status').select(padStatusUp.getAt(padStatus));
		this.formPanel.form.findField('useType').select(useTypeCombo.getAt(useType));
		
		if(brand == "第一代"){
			Ext.getCmp("brandId1").setValue(true);
		}else if(brand == "第二代"){
			 Ext.getCmp("brandId2").setValue(true);
		}else if(brand == "第三代"){
			 Ext.getCmp("brandId3").setValue(true);
		}
		
	},

	cancel : function() {
		this.close();
	},
	update : function(){
			if (this.formPanel.getForm().isValid()) {

//				var temp = /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/;
//				if (!temp.test(Ext.getCmp("macAddress").getValue()))
//				{
//				 Ext.Msg.alert('',"MAC地址不正确请核对后重新填写！");
//			     return;
//				}
				
				var mac= Ext.getCmp("macAddress").getValue();
				var macs = new Array();
			    macs = mac.split(":");
			    if(macs.length != 6){
			    	Ext.Msg.alert('',"MAC地址不正确请核对后重新填写！");
			        return false;
			    }
				
				var useType = Ext.getCmp("useType").getValue();
				var useTypeDesc = Ext.getCmp("useType").getRawValue();
				this.formPanel.form.findField('useType').setValue(useType);
				this.formPanel.form.findField('useTypeDesc').setValue(useTypeDesc);
				var brandName = Ext.getCmp("brandId").getValue(); 
				this.formPanel.form.findField('brandName').setValue(brandName);
				
				this.formPanel.getForm().submit({
					success : function(form, action) {
						if(action.result.success == "true"){
							Ext.MessageBox.alert('提示', action.result.obj);  
							Ext.getCmp("padBaseinfoUpdateId").close();
							Ext.getCmp("padBaseinfoGridPanel").store.reload();
						}else if(action.result.success == "false"){
							if(action.result.errorCode == "10000"){
								Ext.Msg.alert('提示',action.result.memo);
							}else if(action.result.errorCode == "00000"){
								Ext.Msg.alert('提示',action.result.memo);
							}else{
								Ext.Msg.alert('提示',"pad信息修改失败！");
							}
						}
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('提示', action.result.obj);
					}
				});
			}
	}
	
});
