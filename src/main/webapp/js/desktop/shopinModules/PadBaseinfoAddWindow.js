/**
 * 添加PAD基本信息
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadBaseinfoAddWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		var me = this;

		this.superclass.constructor.call(this, {
			title :'添加PAD基本信息',
			height : 310,
			id:'padBaseinfoAddId',
			width  : 420,
			constrain : true,
			plain : true,
			layout:"fit",
			modal: true,
			maximizable:true,
			items : [ this.formPanel],
			buttons :[
				{
					text : '保存',
					handler : function() {
						me.save();
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
		
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		
		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/padBaseinfo/save',
				layout : 'anchor',
				defaults : {
					anchor : '100%',
					labelAlign : 'right',
						width:'310'
				},
				id:"addForm",
				bodyPadding: "25 15 15 25",
				items: [
	                	{
	                    xtype: 'container',
	                    layout: 'hbox',
	                    items: [
	                    	{
	                    		xtype : 'textfield',
								name : 'sid',
								fieldLabel : 'SID',
								hidden:true
							},
							{
	                    		xtype : 'textfield',
								name : 'username',
								value : username,
								hidden:true
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
								afterLabelTextTpl: required,
								allowBlank : false
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
				                	xtype:"combo",
									fieldLabel:'所属批次',
									labelAlign:"left",
									width :300,
									id:"padInputInfoCombo2",
									afterLabelTextTpl: required,
									allowBlank : false,
									store:new Ext.data.Store({
							        	autoLoad : true,
										proxy : {
											type : 'ajax',
											api : {
												read : _appctx + '/padPurchaseInfo/getPadPurchaseInfo.json',
											},
											reader : {
												type: 'json',
							 					root: 'list'
											}
										},
										fields : ["padPurchaseBatchNo"]
							    	}),
									valueField: 'padPurchaseBatchNo',//具体值
									displayField:"padPurchaseBatchNo",//显示的值
									hiddenName:'padPurchaseBatchNo',
									triggerAction : 'all',
									name:'padPurchaseBatchNo',
									typeAhead: true,
									queryMode: "local"
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
										emptyText:"仅允许数字0~9,字母a~f",
										regex:/^([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}$/,
										regexText:"输入字符应在数字0~9,字母a~f范围内"
									}
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
		                    			xtype: 'textfield',
										name : 'purchaseOrderno',
										width : 300,
										fieldLabel : '采购订单号'
									}
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
										fieldLabel:"状态",
										xtype:"combo",
										width : 300,
										id:"padStatus",
										afterLabelTextTpl: required,
										
										store:new Ext.data.ArrayStore({
											id:"padStatusCodeStore",
											autoLoad:true,
											fields: ['padStatusCode','value'],
											data : [
											['0',"在库"],/*['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"],['5','在途']*/
											],
										}),
										
										
										valueField: 'padStatusCode',
										displayField:'value',
										hiddenName:'padStatusCode',
										editable:false,
										triggerAction : 'all',
										name:'padStatus',
										mode:'local',
										allowBlank : false
									}
			                    ]
			                },{
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
//										afterLabelTextTpl: required,
										store:new Ext.data.ArrayStore({
											fields: ['useTypeCode','value'],
											data : [
											['0',"导购"],['1',"主管"],['2',"内衣功能区"],['3',"大场"]
											]
										}),
										valueField: 'useTypeCode',
										displayField:'value',
										hiddenName:'useTypeCode',
										triggerAction : 'all',
										name:'useType',
										mode:'local',
										editable:false,
//										allowBlank : false
									},
									{
		                    			xtype : 'textfield',
										name : 'useTypeDesc',
										fieldLabel : 'useTypeDesc',
										hidden:true
									}
			                    ]
			                }*/,{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                }/*,{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"品牌",
										xtype:"radiogroup",
										width : 300,
										id:'brand',
										items: [
						                        { boxLabel: "第一代", name: "brandName", inputValue: "第一代" },
						                        { boxLabel: "第二代", name: "brandName", inputValue: "第二代" },
						                        { boxLabel: "第三代", name: "brandName", inputValue: "第三代" ,checked:true}
						                    ]
									},
									{
		                    			xtype : 'textfield',
										name : 'brandName',
										fieldLabel : 'brandName',
										hidden:true
									}
			                    ]
			                }*/
					]
					});
		Ext.data.StoreManager.get("padStatusCodeStore").on("load",function(){
			Ext.getCmp("padStatus").select(this.getAt(0));    //自动选择第一项
		});
		
	},

	cancel : function() {
		this.close();
	},
	save : function(){
			if (this.formPanel.getForm().isValid()) {
				
//				var temp = /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/;
//				if (!temp.test(Ext.getCmp("macAddress").getValue()))
//				{
//					 Ext.Msg.alert('',"MAC地址不正确请核对后重新填写！");
//				     return;
//				}
				
				var mac= Ext.getCmp("macAddress").getValue();
				var macs = new Array();
			    macs = mac.split(":");
			    if(macs.length != 6){
			    	Ext.Msg.alert('',"MAC地址不正确请核对后重新填写！");
			        return false;
			    }
			    
				
				this.formPanel.getForm().submit({
					success : function(form, action) {
						
						if(action.result.success == "true"){
							Ext.Msg.alert('提示',"pad信息添加成功！");
							Ext.getCmp("padBaseinfoAddId").close();
							Ext.getCmp("PadInfoInputView1").store.reload();
							Ext.StoreMgr.get("padPurchaseInfoStoreOfPPV").reload();  //入库刷新
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
					failure : function(form, action) {
						Ext.create('Ext.ux.window.Notification', {
							position: 'tr',
							useXAxis: true,
							cls: 'ux-notification-light',
							iconCls: 'ux-notification-icon-error',
							closable: true,
							title: '',
							html: '添加PAD基本信息失败，请稍后重试！',
							slideInDuration: 800,
							slideBackDuration: 1500,
							autoCloseDelay: 4000,
							slideInAnimation: 'elasticIn',
							slideBackAnimation: 'elasticIn'
						}).show();
					}
				});
			}
	}
	
});
