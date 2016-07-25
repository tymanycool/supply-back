/**
 * pad信息报表
 * by syk
 */
Ext.define("ShopinDesktop.PadInfoReportView",{
	extend:"Ext.grid.Panel",
	baseUrl:_appctx,
	tbar:null,
	userAuthId:shopid,

	chart:null,
	columns:null,
	userAuthName:shopname,
	init : function(){
	},
	constructor:function(config){
		config = config || {};//保证了config能访问到属性，否则需要判断
		Ext.apply(this,config);
		this.initComponents();
		this.superclass.constructor.call(this,{
			id:"PadInfoReportView1",
			tbar: this.tbar,
			columns:this.columns,
			store:this.reportStore,//TODO
			viewconfig:{
				enableTextSelection : true
			},
			items:[
				this.chart,
			]
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		var userAuthId = this.userAuthId;
		var userAuthName = this.userAuthName;
		var shopStore = new Ext.data.JsonStore({
//			autoLoad : true,
			proxy : {
				type : 'ajax',
				api : {
					read : _appctx + '/guideSupply/getShopsList2',
				},
				idParam : 'sid',
				reader : {
					type : 'json',
					root : 'result'
				}
			},
			fields: ["sid", "shopName"]
		});
		this.reportStore = Ext.create("Ext.data.Store",{
			autoLoad:true,
			id : 'reportStoreOfPRV',
			pageSize:20,
			clearOnPageLoad:true,   //默认为true
			fields:[
			        {name:"shopName",defaultValue:shopname},
			        {name:"InStop",defaultValue:0},
			        {name:"InPassage",defaultValue:0},
			        {name:"InRepair",defaultValue:0},
			        {name:"InLost",defaultValue:0},
			        {name:"InShop",defaultValue:0},
			        {name:"InStore",defaultValue:0},
			        {name:"totalProperty",defaultValue:0}],//字段s
			proxy:{
				type:"ajax",
				url:_appctx+"/padBaseinfo/selectPadInfoReport",
				getMethod:function(){
					return "POST";
				},
				reader:{
					type:"json",
					root:"obj",
				},
			},
			listeners:{
				beforeload: function(store, operation, eOpts) {
					Ext.apply(store.proxy.extraParams, {
						shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPRV').getValue()
			        });
				},
				exception: function(proxy, response, operation, eOpts) {
					if (response.status != 200) {
						Ext.MessageBox.alert("Error", "加载列表失败！");
					}
				}
			}
		});
		var reportStore = this.reportStore;
		//bar
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"padInfoInputTbarOfPRV",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 5,
				items:[
				   {
						xtype:"combo",
						fieldLabel : '门店',
						labelWidth: 30,
						labelAlign:"right",
						width : 200,
						id:"padShopIdOfPRV",
						store : shopStore,
						valueField: 'sid',
						displayField:'shopName',
						disabled:userAuthId!='1000'?true:false,
						hiddenName:'sid',
						triggerAction : 'all',
						name:'shop',
						mode:'local'
					},{
		            	fieldLabel:"选择系统",
						xtype:"radiogroup",
						labelWidth: 80,
						labelAlign:"right",
						width : 300,
						id:'padHasBatchNoOfPAV',
						items: [
						        { boxLabel: '全部', name: 'rb', inputValue: '',columnWidth:3},
								{ boxLabel: '旧系统', name: 'rb', inputValue: 'isOld',columnWidth:4},
								{ boxLabel: '新系统', name: 'rb', inputValue: 'isNew',columnWidth:4}
		                    ],
						triggerAction : 'all',
						editable:false,
						mode:'local'
				        },{
							text:'查找',
							width:100,
							pressed: true,
							ctCls: 'x-btn-over',
							handler:function(){
		//							alert(Ext.getCmp('padHasBatchNoOfPAV').getChecked()[0].getGroupValue());
								var selected = Ext.getCmp('padHasBatchNoOfPAV').getChecked()[0];
								var hasBatchNoNew = "";
								if(selected != null){
									hasBatchNoNew = selected.getGroupValue();
								}
								
								reportStore.load({
								 	 params:{
								 		shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPRV').getValue(),
								 		shopName : userAuthId!='1000'?userAuthName:Ext.getCmp('padShopIdOfPRV').getRawValue(),
								 		hasBatchNo : hasBatchNoNew
								 	 }
								});
							}
					}
					]}]
			});
			this.columns = [ new Ext.grid.RowNumberer({
							width : 40
						}),{
							header : '门店',
							dataIndex : 'shopName',
							width : 80,
							sortable : true,
							
						},{
							header : '在库',
							dataIndex : 'InStore',
							width : 80,
							sortable : true
						},{
							header : '卖场',
							dataIndex : 'InShop',
							width : 80,
							sortable : true
						}, {
							header : '送修',
							dataIndex : 'InRepair',
							width : 80,
							sortable : true
						}, {
							header : '停用',
							dataIndex : 'InStop',
							width : 80,
							sortable : true
						}, {
							header : '丢失',
							dataIndex : 'InLost',
							width : 80,
							sortable : true
						}, {
							header : '在途',
							dataIndex : 'InPassage',
							width : 80,
							sortable : true
						}, {
							header : '总计',
							dataIndex : 'totalProperty',
							width : 80,
							sortable : true
						}];
		if(userAuthId!='1000'){
			Ext.getCmp("padShopIdOfPRV").setValue(userAuthName);
		}
	},
	listeners:{
		itemdblclick:function(view, record,item){
		}
	}
});