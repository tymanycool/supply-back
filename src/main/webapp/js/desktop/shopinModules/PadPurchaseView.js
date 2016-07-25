Ext.define("ShopinDesktop.PadPurchaseView",{
	extend:"Ext.grid.Panel",
	baseUrl:_appctx,
	tbar:null,
	pageBar:null,
	columns:null,
	constructor:function(config){
		config = config || {};//保证了config能访问到属性，否则需要判断
		Ext.apply(this,config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id:"PadPurchaseView1",
			columns:this.columns,
			tbar: this.tbar,
			bbar : this.pageBar,
			store:this.padPurchaseInfoStore,
			viewconfig:{
				enableTextSelection : true
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		
		this.padPurchaseInfoStore = Ext.create("Ext.data.Store",{
			autoLoad : true,
			pageSize : 20,			//limit
			clearOnPageLoad : true,
			id:"padPurchaseInfoStoreOfPPV",
			fields : [
			          "sid","padPurchaseBatchNo","padBrand","padSupply","padPurchaseTime","padPurchaseNum","padEnteredNum","padOperator","padOperatorTime"
					],
 			proxy: {
				type: "ajax",
				url : _appctx + "/padPurchaseInfo/getPadPurchaseInfo.json",
				getMethod: function(){
					return 'POST';
				},
				reader: {
					type: "json",
					root : "list",
					totalProperty :"total"
				},
				listeners: {
					
					exception: function(proxy, response, operation, eOpts) {
						if (response.status != 200) {
							Ext.MessageBox.alert("Error", "加载列表失败！");
						}
					}
				}
			}
		});
		this.columns=[
				new Ext.grid.RowNumberer({
							width:40
						}),
				{
					header : "padsid",
					dataIndex : "sid",
					hidden : true,
					width:20
				},{
					header : "批次号",
					dataIndex : "padPurchaseBatchNo",
					width:120,
				},{
					header : "PAD品牌",
					dataIndex : "padBrand",
					width:120
				},{
					header : "PAD厂商",
					dataIndex : "padSupply",
					width:120
				},
				{
					header : "订购时间",
					dataIndex : "padPurchaseTime",
					width:120
//					renderer:Ext.util.Format.dateRenderer('Y-m-d')
				},{
					header : "订购数量",
					dataIndex : "padPurchaseNum",
					width:120
				},{
					header : "已录入数量",
					dataIndex : "padEnteredNum",
					width:120
				},{
					header : "操作人",
					dataIndex : "padOperator",
					width:120
				},{
					header : "操作时间",
					dataIndex : "padOperatorTime",
					width:120
				}/*,{
					text:"操作",
					xtype:'actioncolumn',
					sortable: false,
					width:80,
					align:'center',
					items:[{
						icon:_appctx+'/images/delete.gif',
						handler:function(grid, rowIndex, colIndex){
							var record = grid.getStore().getAt(rowIndex);
							deletePadPurchaseInfo(record.data.sid);   禁用删除功能。
						}
					}]
				}*/
		];
		var myStore = this.padPurchaseInfoStore;
		//删除
		function deletePadPurchaseInfo(sid){
				var caller = this.caller;
				Ext.MessageBox.confirm('警告','确定要删除吗？',function(btn){
					if('yes'==btn){
						Ext.Ajax.request({
							url:_appctx +'/padPurchaseInfo/deletePadPurchaseInfo.json',
							success:function(response){
								Ext.Msg.alert('成功',"删除成功！");
								myStore.reload();
							},
							failure:function(response){
								Ext.Msg.alert('失败',"删除失败！");
							},
							//参数可以为JSON对象或键值对
							params:{sid:sid}
						});
					}
				});
		}
		//bar
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"padPurchaseTbar",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 5,
				items:[
					{
						text:'添加',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){
							Ext.create("ShopinDesktop.AddPadPurchasInfoWindow", {
								id: "addPadPurchasInfoWindow",
								baseUrl: this.baseUrl,
								caller: this
							}).show();
						}
					}]
				}]
			});
		//分页bar
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padPurchaseInfoStore,
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