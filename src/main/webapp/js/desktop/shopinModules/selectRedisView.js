Ext.define("ShopinDesktop.selectRedisView",{
	extend:"Ext.grid.Panel",
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store','Ext.form.FormPanel'],
	baseUrl:_appctx,
	tbar:null,
//	formPanel:null,
	pageBar:null,
	columns:null,
	constructor:function(config){
		config = config || {};//保证了config能访问到属性，否则需要判断
		Ext.apply(this,config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id:"selectRedisView1",
			columns:this.columns,
			tbar: this.tbar,
//			formPanel:this.formPanel,
			bbar : this.pageBar,
			viewconfig:{
				enableTextSelection : true
			}  
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
//		
		this.columns=[
				{
					header : "padsid",
					dataIndex : "sid",
					hidden : true,
					width:20
				}
		];
		//bar
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"padPurchaseTbar",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 5,
				items:[
					{
						xtype : "textfield",
						id:"inputSelectKey",
						fieldLabel:"输入缓存KEY值",
						labelAlign:"right",
						labelWidth: 100,
						width : 200,
					},{

						xtype: "button",
						icon:baseUrl + "/images/find.png",
						pressed: true,
						text: "查询",
						width:80,
						margin:"0 10 0 5",
						handler: function() {
							var o=Ext.getCmp('selectRedisView1');
							o.el.mask('正在查找...', 'x-mask-loading');
							var rediskey = Ext.getCmp("inputSelectKey").getValue();
							Ext.Ajax.request({
								type: "ajax",
								method: 'POST',
								url : _appctx + "/editRedis/selectRedisByKey.json",
								params:{
									rediskey : rediskey,
								},
								success: function(response){
								var result=Ext.decode(response.responseText);
								if(result.success=='true'){
									o.el.unmask();
									Ext.getCmp('showMessage').setValue(result.obj);  
									Ext.Msg.alert('成功',"查询对应的缓存数据成功！");
								}else{
									o.el.unmask();
									Ext.Msg.alert('失败',"查询对应的缓存数据失败！");
								}
								},
								failure:function(){
									o.el.unmask();
								Ext.Msg.alert('错误','与后台联系出现问题');
								},
								});
//							myStore.load({
//								params:{
//									rediskey : rediskey,
//								}
//							});
						}
					},{
				        xtype     : 'textareafield',
				        id        : 'showMessage',
				        grow      : true,
				        name      : 'message',
				        fieldLabel: '缓存数据',
				        anchor    : '100%',
				        width: 880,
				        height:490
				    }
					]
				}]
			});
		 
		//分页bar
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padPurchaseInfoStore1,
			displayInfo : true
		});
	},
	listeners:{
		itemdblclick:function(view, record,item){
			if(record !=null){
				Ext.create("ShopinDesktop.UpdatePadInfoWindow", {
					id: "UpdatePadInfoWindow",
					baseUrl: this.baseUrl,
					caller: this,
					record: record
				}).show();
			}else{
				Ext.MessageBox.alert("提示","请选择一条记录");
			}
		}
	}
});