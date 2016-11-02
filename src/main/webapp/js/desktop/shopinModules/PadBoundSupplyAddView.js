/**
 * 添加绑定功能
 */
Ext.define('ShopinDesktop.PadBoundSupplyAddView', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	sid :null,
//	userAuth:shopid,   //登录人所属门店id
//	userAuthName:shopname, //登录人所属门店name
	record:null,
	padNo :null,
	formPanel:null,
	useType:null,
    useTypeDesc:null,
    padShopId:null,   // 当前PAD 门店id
    padShopName:null, //当前PAD 门店name
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;     
        sid = config.sid;
        record=config.record,
        padNo = config.record.data.padNo;
    	useType=config.record.data.useType,
        useTypeDesc=config.record.data.useTypeDesc,
        padShopId=config.record.data.shopId,
        padShopName=config.record.data.shopName,
        this.initCmps(me);
		this.superclass.constructor.call(this, {
			title:"添加绑定供应商",
			id:'padBoundSupplyAddView',
			height : 310,
			width  : 420,
			plain : true,
			resizable :true,
			constrain : true,
			layout:"fit",
			modal: true,
			maximizable:true,
			items : [ me.formPanel ]
			
		});
	},
	initCmps : function(me) {
		
		var useTypeStore = Ext.create('Ext.data.ArrayStore',{
			autoLoad:true,
			  id:'useTypeOfPBSAV',
			  fields:['value','text'],
			  data:[['0',"导购"],['1',"主管"],['2',"内衣功能区"],['3',"大场"],['4',"客服"]],
		  });
		
		  
		
		me.formPanel = Ext.create('Ext.form.Panel', {
			height : 310,
			width  : 420,
			layout : 'anchor',
			bodyPadding: '15 15 15 15',
			defaults : {
				anchor : '100%',
				labelAlign : 'right'
			},
			items: [ 
					{
	        			xtype: 'textfield',
						name : 'shopName',
						id:'shopNameOfPBSAV',
						width : 300,
						fieldLabel : '门店',
						readOnly : true,
						value: padShopName
					},{
						xtype:'hidden',
						name:'shopId',
						value:padShopId
					},{
						xtype:'hidden',
						name:'username',
						value:username
					},{
						xtype:'hidden',
						name:'userSid',
						value:userSid
					},{
						xtype:'hidden',
						name:'padNo',
						value:padNo
					},{
						xtype:'hidden',
						name:'flag',
					},{
						xtype:'hidden',
						name:'supplyName',
					},{
						xtype:'hidden',
						name:'useTypeDesc',
					},{
							xtype:"combo",
							fieldLabel : '供应商',
							width:300,
							editable:false,
							id:'supplyinfoNameOfPBSAV',
							emptyText: '请选择...',
							store:new Ext.data.Store({
					        	autoLoad:true,
								proxy : {
									type : 'ajax',
									api : {
										read : _appctx + '/guideSupply/getSupplyInfo?shopId='+padShopId,
									},
									reader : {
										type: 'json',
					 					root: 'list'
									}
								},
								fields : ["supplyinfoName","supplyinfoSid"]
					    	}),
							valueField: 'supplyinfoSid',
							displayField:'supplyinfoName',
							name:'supplyId',
							hiddenName:'supplyId',
							triggerAction : 'all',
							mode:'local',
							typeAhead: true,
							allowBlank : false,
							listeners: {
                               beforequery : function(e){
					                    var combo = e.combo;
						                if(!e.forceAll){
							                var value = e.query;
							                combo.store.filterBy(function(record,id){
								                var text = record.get(combo.displayField);
								                return (text.indexOf(value)!=-1);
							                });
							                combo.expand();
							                return false;
						                }
					               }
                            }
          	  },{
          		  xtype:'combo',
          		  fieldLabel:'使用类型',
          		  id:'useTypeId',
          		  name:'useTypeId',
          		  name:'useType',
          		  hiddenName:'useType',
          		  allowBlank:false,
          		  displayField:'text',
          		  valueField:'value',
          		  emptyText:'请选择',
          		  width:300,
          		  store:useTypeStore
          	  }],
		    buttons: [{
				text : '重置',
				handler : function() {
					me.formPanel.getForm().reset();
				}
			},{
		        text: '取消',
		        handler: function() {
		            me.close();
		        }
		    },{
		        text: '确认',
		        formBind: true, 
		        disabled: true,
		        handler: function() {
					
		            var form = this.up('form').getForm();
		            if (form.isValid()) {
		            
		            	form.findField('flag').setValue(Ext.getCmp('supplyinfoNameOfPBSAV').getValue());//为了兼容原来的功能
		            	form.findField('supplyName').setValue(Ext.getCmp("supplyinfoNameOfPBSAV").getRawValue());
		            	form.findField('useTypeDesc').setValue(Ext.getCmp('useTypeId').getRawValue());
		                form.submit({
		                    clientValidation: true,
		                    url: _appctx + '/padSupplyRelation/savePadSupply',
		                    success: function(form, action) {
								Ext.Msg.alert("提示","操作成功！")
								Ext.getCmp('padBoundSupplyGridId').store.reload();
								Ext.getCmp('padBaseinfoGridPanelOfPCV').store.reload();
		                    },
		                    failure: function(form, action) {
								Ext.Msg.alert("提示","操作失败！")
		                    }
		                });
		            };
		            me.close();
		        }
		    }]
		});
		 
		 
		 if(useType != null && useType != '' && useType != '-1'){
		            me.formPanel.form.findField('useType').select(useTypeStore.getAt(useType));
//		            combo.setValue().fireEvent('select',combo,combo.getStore().getById(useType));
		    	}
		 
	}
});
