/**
 * 修改PAD基本信息-信息查看模块
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadInfoUpdateWindowOfPCV', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	sid :null,
	supplyName:null,
	padNo:null,
	me:null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		 me = this;
		 record = config.record;
			sid = record.data.sid;
			padNo = record.data.padNo;
			macAddress = record.data.macAddress;
			purchaseOrderno = record.data.purchaseOrderno;
			padStatus = record.data.padStatus;
			useType = record.data.useType;
//			brand = record.data.brand;
			supplyName = record.data.supplyName;
		 
		this.initComponents(me);
		
		this.superclass.constructor.call(this, {
			title :'修改PAD基本信息',
			height : 200,
			id:'PadInfoUpdateWindowOfPCVId',
			width  : 350,
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
	initComponents : function(me) {
		var arr = allResource.split(',');
		function hasPermission(permission){
			return Ext.Array.contains(arr,permission);
		}
		
		
		
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		
		var padStatusUp = new Ext.data.ArrayStore({
			
			fields: ['padStatusCode','value'],
			data : [
			/*['0',"在库"],['1',"卖场"],*/['2',"送修"],['3',"停用"],['4',"丢失"]/*,['5',"在途"]*/
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
				fieldDefaults: {
					anchor : '100%',
					labelAlign : 'left',
					width:300,
					labelWidth:80
				},
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
								fieldLabel : 'PAD编号',
								value : padNo,
								afterLabelTextTpl: required,
								readOnly:hasPermission("PadInfoInputView")?false:true,
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
										fieldLabel : 'MAC地址',
										afterLabelTextTpl: required,
										allowBlank : false,
										readOnly :hasPermission("PadInfoInputView")?false:true,
										value : macAddress
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
										id:"status",
										store : padStatusUp,
										emptyText:'请先解绑，才可以更新状态。不修改此项，可不选择。',
										valueField: 'padStatusCode',
										displayField:'value',
										hiddenName:'padStatusCode',
										triggerAction : 'all',
										name:'padStatus',
										editable:true,
										mode:'local',
//										afterLabelTextTpl: required,
										allowBlank : true
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
										id:"useTypeOfPadUpdateOfPCV",
										emptyText:'不修改使用类型，可不选择',
										hidden:(padStatus == "1")?false:true,
										disabled:(padStatus == "1")?false:true,  //是否传到后台
										store : useTypeCombo,
										valueField: 'useTypeCode',
										displayField:'value',
										hiddenName:'useTypeCode',
										triggerAction : 'all',
										name:'useType',
										mode:'local',
										afterLabelTextTpl: required,
										editable:true,
										allowBlank : false
									},
									{
		                    			xtype : 'textfield',
										name : 'useTypeDesc',
										fieldLabel : 'useTypeDesc',
										hidden:true
									},
			                    ]
			                }
						]
				});
		
//		me.formPanel.form.findField('status').select(padStatusUp.getAt(padStatus));
		me.formPanel.form.findField('useType').select(useTypeCombo.getAt(useType)); //数据回显
		
		/*if(padStatus != 1){ //非卖场
			alert('非卖场');
			me.formPanel.remove("useTypeOfPadUpdateOfPCV");
		}*/
	},

	cancel : function() {
		this.close();
	},
	update : function(me){
		var selectedStatus = Ext.getCmp("status").getValue();  //选择状态
		if(selectedStatus){
//			alert(supplyName);
//			alert(me.supplyName);
			if(supplyName !=null && supplyName != ''){ //和供应商有绑定关系
				Ext.Msg.alert('','请先解绑，才可以修改PAD状态');
				return;
			}
		}
			if (this.formPanel.getForm().isValid()) {
				var mac= Ext.getCmp("macAddress").getValue();
				var macs = new Array();
			    macs = mac.split(":");
			    if(macs.length != 6){
			    	Ext.Msg.alert('',"MAC地址不正确请核对后重新填写！");
			        return false;
			    }
			 var isHidden = Ext.getCmp('useTypeOfPadUpdateOfPCV').isHidden();
			 if(!isHidden){
				 var useType = Ext.getCmp("useTypeOfPadUpdateOfPCV").getValue();
					var useTypeDesc = Ext.getCmp("useTypeOfPadUpdateOfPCV").getRawValue();
					this.formPanel.form.findField('useType').setValue(useType);
					this.formPanel.form.findField('useTypeDesc').setValue(useTypeDesc);
			 }
				this.formPanel.getForm().submit({
					success : function(form, action) {
						if(action.result.success == "true"){
							Ext.MessageBox.alert('提示', action.result.obj);
							Ext.getCmp("PadInfoUpdateWindowOfPCVId").close();
							Ext.getCmp("padBaseinfoGridPanelOfPCV").store.reload();
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
