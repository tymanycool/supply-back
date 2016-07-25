/**
 * 修改PAD基本信息-信息录入模块
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadInfoUpdateWindowOfPIV', {
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
			height : 200,
			id:'PadInfoUpdateWindowOfPIVId',
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
	initComponents : function() {
		var arr = allResource.split(',');
		function hasPermission(permission){
			return Ext.Array.contains(arr,permission);
		}
		
		var record = this.record;
		var sid = record.data.sid;
		var padNo = record.data.padNo;
		var macAddress = record.data.macAddress;
		var purchaseOrderno = record.data.purchaseOrderno;
//		var padStatus = record.data.padStatus;
		var useType = record.data.useType;
		var brand = record.data.brand;
		
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
								value : username,
								
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
								allowBlank : false,
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
										value : macAddress,
										regex:/^([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}$/,
										regexText:"输入字符应在数字0~9,字母a~f范围内"
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
										emptyText:'不修改状态，可不选此项',
										store : padStatusUp,
										valueField: 'padStatusCode',
										displayField:'value',
										hiddenName:'padStatusCode',
										triggerAction : 'all',
										name:'padStatus',
										editable:false,
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
			                }/*,{
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
										editable:false,
									},
									{
		                    			xtype : 'textfield',
										name : 'useTypeDesc',
										fieldLabel : 'useTypeDesc',
										hidden:true
									},
			                    ]
			                }*/]
				});
		
//		this.formPanel.form.findField('status').select(padStatusUp.getAt(padStatus));
//		this.formPanel.form.findField('useType').select(useTypeCombo.getAt(useType));
	},

	cancel : function() {
		this.close();
	},
	update : function(){
			if (this.formPanel.getForm().isValid()) {
				var mac= Ext.getCmp("macAddress").getValue();
				var macs = new Array();
			    macs = mac.split(":");
			    if(macs.length != 6){
			    	Ext.Msg.alert('',"MAC地址不正确请核对后重新填写！");
			        return false;
			    }
/*				var useType = Ext.getCmp("useType").getValue();
				var useTypeDesc = Ext.getCmp("useType").getRawValue();
				this.formPanel.form.findField('useType').setValue(useType);
				this.formPanel.form.findField('useTypeDesc').setValue(useTypeDesc);*/
				this.formPanel.getForm().submit({
					success : function(form, action) {
						if(action.result.success == "true"){
							Ext.MessageBox.alert('提示', action.result.obj);
							Ext.getCmp("PadInfoUpdateWindowOfPIVId").close();
							//录入界面表格刷新
							 Ext.StoreMgr.get('padInfoGridIdOfPIV').reload();
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
