Ext.define("ShopinDesktop.delRedisView",{
	extend:"Ext.grid.Panel",
	baseUrl:_appctx,
	tbar:null,
//	pageBar:null,
	columns:null,
	constructor:function(config){
		config = config || {};//保证了config能访问到属性，否则需要判断
		Ext.apply(this,config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id:"delRedisView1",
			columns:this.columns,
			tbar: this.tbar,
//			bbar : this.pageBar,
//			store:this.padPurchaseInfoStore2,
			viewconfig:{
				enableTextSelection : true
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		
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
			id:"delCacheTbar",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 5,
				items:[
					{
						xtype : "textfield",
						id:"padNoIdOfPCV1",
						fieldLabel:"输入缓存KEY值",
						labelAlign:"right",
						labelWidth: 100,
						width : 200,
					},{

						xtype: "button",
						icon:baseUrl + "/images/remove.gif",
						pressed: true,
						text: "清除缓存",
						width:80,
						margin:"0 10 0 5",
						handler: function() {
							var o=Ext.getCmp('delRedisView1');
							o.el.mask('正在清空...', 'x-mask-loading');
							var rediskey = Ext.getCmp("padNoIdOfPCV1").getValue();
							Ext.Ajax.request({
								type: "ajax",
								method: 'POST',
								url : _appctx + "/editRedis/delRedisByKey.json",
								params:{
									rediskey : rediskey,
								},
								success: function(response){
								var result=Ext.decode(response.responseText);
								if(result.success=='true'){
									o.el.unmask();
									Ext.Msg.alert('成功',"清除对应的缓存数据成功！");
								}else{
									o.el.unmask();
									Ext.Msg.alert('失败',"清除对应的缓存数据失败！");
								}
								},
								failure:function(){
									o.el.unmask();
								Ext.Msg.alert('错误','与后台联系出现问题');
								}
								});
//							myStore.load({
//								params:{
//									rediskey : rediskey,
//								}
//							});
						}
					}]
				}]
			});
		 
		//分页bar
//		this.pageBar = Ext.create("Ext.toolbar.Paging", {
//			store : this.padPurchaseInfoStore2,
//			displayInfo : true
//		});
	}
//	listeners:{
//		itemdblclick:function(view, record,item){
//			if(record !=null){
//				Ext.create("ShopinDesktop.UpdatePadInfoWindow", {
//					id: "UpdatePadInfoWindow",
//					baseUrl: this.baseUrl,
//					caller: this,
//					record: record
//				}).show();
//			}else{
//				Ext.MessageBox.alert("提示","请选择一条记录");
//			}
//		}
//	}
});