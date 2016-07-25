/**
 * PAD信息批量修改
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadBatchUpdateView', {
	extend:"Ext.window.Window",
	requires : ['Ext.form.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	padNos:'',
	formPanel:null,
	padBaseInfoStore:null,
	padStatusStore:null,  //
	useTypeStore:null,
	username:username,
	userSid:userSid,
	me:null,
	init : function(){

	},
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		me = this;
		this.superclass.constructor.call(this, {
			title :'PAD批量修改',
			height : 200,
			id:'padBatchUpdateView',
			width  : 420,
			constrain : true,
			layout:"fit",
			modal: true,
			maximizable:true,
			plain : true,
			items : [ this.formPanel],
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		padNos = this.padNos;
		
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		var padStatusStore = new Ext.data.ArrayStore({
			fields: ['padStatusCode','text'],
			data : [
			/*['0',"在库"],['1',"卖场"],*/['2',"送修"],['3',"停用"],['4',"丢失"]/*,['5',"在途"]*/
			]
		});
		
		var useTypeStore = new Ext.data.ArrayStore({
			fields: ['useTypeCode','text'],
			data : [
			        ['0',"导购"],['1',"主管"],['2',"内衣功能区"],['3',"大场"]
			        ]
		});
		
		this.formPanel = Ext.create("Ext.form.Panel",{
			bodyPadding: '15 15 15 15',
		    width: 200,
		    url: baseUrl+'/padBaseinfo/padBaseinfoBatchUpdate',
		    layout: 'anchor',
		    defaults: {
		        anchor: '100%'
		    },
		    defaultType: 'textfield',
		    items: [{
		    	xtype:"combo",
		        fieldLabel: '状态',
		        store:padStatusStore,
		        name: 'padStatus',
		        id:'padStatus',
		        allowBlank: false,
		        hiddenName:"padStatus",
		        triggerAction : 'all',
		        displayField: 'text',
		        valueField: 'padStatusCode',
		        afterLabelTextTpl: required,
		        model:"local"
		    },/*{
		    	xtype:"combo",
		    	store:useTypeStore,
		        fieldLabel: '使用类型',
		        name: 'useType',
		        id: 'useTypeId',
		        allowBlank: false,
		        triggerAction : 'all',
		        hiddenName:"useType",
		        displayField: 'text',
		        valueField: 'useTypeCode',
		        afterLabelTextTpl: required,
		        model:"local"
		    },{
		    	xtype : 'textfield',
		    	fieldLabel: 'useTypeDesc',
		    	name: 'useTypeDesc',
		        id: 'useTypeDesc',
				hidden:true
		    },*/{
		    	xtype : 'textfield',
		    	fieldLabel: 'padNos',
		    	name: 'padNos',
		        id: 'padNos',
				hidden:true,
				value: padNos
		    },{
		    	xtype : 'textfield',
		    	name: 'username',
		        id: 'username',
				hidden:true,
				value: username
		    },{
		    	xtype : 'textfield',
		    	name: 'userSid',
		        id: 'userSid',
				hidden:true,
				value: userSid
		    }],
		    buttons: [{
		        text: '取消',
		        handler: function() {
		            me.cancel();
		        }
		    }, {
		        text: '确认',
		        formBind: true, //only enabled once the form is valid
		        disabled: true,
		        handler: function(){
		        	me.update();
		        }
		    }]
		});
	},
	
	cancel : function() {
		me.close();
	},
	update : function(){
		/*var useTypeDesc = Ext.getCmp("useTypeId").getRawValue();
		me.formPanel.getForm().findField("useTypeDesc").setValue(useTypeDesc);*/
		Ext.Msg.wait("正在拼命更新，请等待");
		if (me.formPanel.getForm().isValid()) {
			me.formPanel.getForm().submit({
				 success: function(form, action) {
					 var flag = action.result.success;
					 if(flag =="true"){
						 //更新成功,关闭窗口.
						 Ext.MessageBox.alert("提示",action.result.obj);
						 Ext.getCmp("padBatchUpdateView").close();
						 Ext.getCmp("padBaseinfoGridPanelOfPCV").store.reload(); //信息查看grid重新加载
					 }else if(flag == "false"){
						 //更新失败，，显示失败记录
						 if(action.result.errorCode =="00000"){
							 Ext.MessageBox.alert("提示",action.result.memo);
						 }else if(action.result.errorCode =="10000"){
							 Ext.MessageBox.alert("提示",action.result.memo);
						 }else{
							 Ext.MessageBox.alert("pad批量更新失败");
						 }
					 }
                  },
                  failure: function(form, action) {
                	  Ext.MessageBox.alert("pad批量更新失败");
                  }
			});
		}
	}
});
