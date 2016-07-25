/**
 * PAD调拨管理
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadAllocateView', {
	extend:"Ext.grid.Panel",
	baseUrl: _appctx,
	tbar:null,
	columns:null,
	padBaseInfoStore:null,
	padNo : null,
	macAddress : null,
	purchaseOrderno : null,
	padStatusText : null,
	sid : null,
	userAuthId:shopid,//获取登录用户所处门店
	userAuthName:shopname,
	init : function(){
	},
	constructor: function(config) {
		config = config || {};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'PadAllocateView1',
			tbar: this.tbar,
			bbar : this.pageBar,
			store:this.padBaseInfoStore,
			columns:this.columns,
			viewconfig:{
				enableTextSelection : true
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		
		this.selModel = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var shopStore = new Ext.data.JsonStore({
			autoLoad : true,
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
		
		
		
		this.padBaseInfoStore = Ext.create("Ext.data.Store",{
			id : 'padBaseInfoGridIdOfPAV',
			autoLoad : true,
			pageSize : 20,
			fields : [
					"sid","padNo","macAddress","padPurchaseBatchNo","purchaseOrderno","brand","padStatus","createTime","shopName","targetShopId","targetShopName",
					"supplyName","useType","useTypeDesc","operator","brand","shopId"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/padBaseinfo/padCanAllocateList',
					getMethod: function(){
						return 'POST';
					},
					reader: {
						type: "json",
						root : "obj.list",
						totalProperty :"obj.total"
					},
					listeners: {
						exception: function(proxy, response, operation, eOpts) {
							if (response.status != 200) {
								Ext.MessageBox.alert("Error", "加载列表失败！");
							}
						}
					}
				},
			listeners:{
				beforeload: function(store, operation, eOpts) {
						Ext.apply(store.proxy.extraParams, {
							shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPAV').getValue(),
							targetShopId:Ext.getCmp('padTargetShopIdOfPAV').getValue(),
							supplyId : Ext.getCmp('padSupplySidOfPAV').getValue(),
							padNo : Ext.getCmp('padNoIdOfPAV').getValue(),
							macAddress : Ext.getCmp('macAddressIdOfPAV').getValue(),
							padStatus : Ext.getCmp('padStatusIdOfPAV').getValue(),
//							useType : Ext.getCmp('useTypeIdOfPAV').getValue()
				        });
					},
				load: function(store, records, success, eOpts) {
				}
			}
		});
		
		var padinfoStore = this.padBaseInfoStore;
			
		var comboStore = new Ext.data.ArrayStore({
               fields: ['type', 'state'],
               autoLoad:true,
               data : [['全部',''],['在库',0],['卖场',1],['送修',2],['停用',3]]
               
             });
		
		/**
		 * 修改
		 */
		 function updatePadBaseinfo(record){
			  Ext.create('ShopinDesktop.PadBaseinfoUpdateWindow',{
				    record : record
//					padNo: padNo,
//					macAddress : macAddress,
//					purchaseOrderno : purchaseOrderno,
//					padStatus : padStatusText
				}).show();
		 }
		 
		 /**
		  * 绑定供应商
		  */
		 function boundSupply(){
			Ext.create('ShopinDesktop.PadBoundSupplyWindow',{
						    padNo : padNo
						}).show();
		 }
		 //add by qutengfei for 查看pad安装列表 in 20150723 start
		/**
		  * 查看pad安装列表
		  * for bug
		  * feature https://tower.im/s/9cCdh
		  */
		 function watchPadSoft(){
			Ext.create('ShopinDesktop.WatchPadSoftWindow',{
						    macAddress:macAddress,
						    shopId:shopId
						}).show();
		 }
		 //add by qutengfei for 查看pad安装列表 in 20150723 end
		var checkbox = this.selModel;
		var userAuthId = this.userAuthId;
		var userAuthName = this.userAuthName;
		this.columns=[
						new Ext.grid.RowNumberer({
									width:40
								}),
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'编号',dataIndex:'padNo',width:120,sortable:true},
					{header:'批次号',dataIndex:'padPurchaseBatchNo',width:120,sortable:true},
					{header:'MAC地址',dataIndex:'macAddress',width:150,sortable:true},
					{header:'品牌',dataIndex:'brand',width:100,sortable:true},
//					{header:'采购订单号',dataIndex:'purchaseOrderno',width:100,sortable:true},
//					{header:'PAD品牌',dataIndex:'brand',width:100,sortable:true},
					{header:'门店',dataIndex:'shopName',width:80,sortable:true},
					{header:'目的门店',dataIndex:'targetShopName',width:80,sortable:true},
					{header:'绑定供应商名称',dataIndex:'supplyName',width:200,sortable:true},
					{header:'状态',dataIndex:'padStatus',width:60,sortable:true,
						renderer:function(value){
							if(value == 0) {
								return '在库';
							}
							if(value == 1) {
								return '卖场';
							}
							if(value == 2) {
								return '送修';
							}
							if(value == 3) {
								return '停用';
							}
							if(value == 4){
								return '丢失';
							}
							if(value == 5){
								return '在途'
							}
						}
					},
					{header:'使用类型',dataIndex:'useTypeDesc',width:80,sortable:true},
					{header:'操作人',dataIndex:'operator',width:80,sortable:true},
					{header:'创建时间',dataIndex:'createTime',width:150,sortable:true},
					{
						text:"修改",
						xtype:'actioncolumn',
						sortable: true,
						hidden:true,
						width:50,
						align:'center',
						items :[
							{
								text : '修改',
								tooltip: '修改',
								icon:_appctx+'/images/edit.gif',
								handler:function(grid, rowIndex, colIndex){
									var record = grid.getStore().getAt(rowIndex);
									updatePadBaseinfo(record);
								}
							}
						]	
					},
					{
						text:"绑定供应商",
						xtype:'actioncolumn',
						sortable: true,
						width:80,
						hidden:true,
						align:'center',
						items :[
							{
								text : '绑定供应商',
								xtype : 'button',
								tooltip: '绑定供应商',
								icon:_appctx+'/js/desktop/images/contact_blue_new.png',
								handler:function(grid, rowIndex, colIndex){
									var record = grid.getStore().getAt(rowIndex);
					  				padNo = record.data.padNo;
									boundSupply();
								}
							}
						]	
					},
					{
						text:"查看安装列表",
						xtype:'actioncolumn',
						sortable: true,
						width:80,
						align:'center',
						items :[
							{
								text : '查看安装列表',
								xtype : 'button',
								tooltip: '查看安装列表',
								icon:_appctx+'/images/edit.gif',
								handler:function(grid, rowIndex, colIndex){
									var record = grid.getStore().getAt(rowIndex);
					  				macAddress = record.data.macAddress;
					  				shopId = record.data.shopId;
									watchPadSoft();
								}
							}
						]	
					}
				];
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"padAllocateTbarOfPAV",
			items:[{
					xtype: "buttongroup",
					width:"100%",
					columns: 6,
					items:[
						{
							xtype : "textfield",
							id:"padNoIdOfPAV",
							fieldLabel:"PAD编号",
							labelAlign:"right",
							labelWidth: 60,
							width : 200,
						},
						{
							xtype : "textfield",
							id:"macAddressIdOfPAV",
							fieldLabel:"MAC地址",
							labelAlign:"right",
							labelWidth: 60,
							width : 200,
						},{
							fieldLabel:"状态",
							xtype:"combo",
							labelWidth: 30,
							width : 180,
							labelAlign:"right",
							id:"padStatusIdOfPAV",
							store:new Ext.data.ArrayStore({
								fields: ['padStatusCode','value'],
								data : [
								['-1',"全部"],['0',"在库"]/*,['1',"卖场"]*/,['2',"送修"]/*,['3',"停用"],['4',"丢失"]*/,['5',"在途"]
								]
							}),
							valueField: 'padStatusCode',
							displayField:'value',
							hiddenName:'padStatusCode',
							triggerAction : 'all',
							name:'padStatus',
							mode:'local'
						},
						/*{
							fieldLabel:"使用类型",
							xtype:"combo",
							labelWidth: 60,
							width : 130,
							id:"useTypeIdOfPAV",
							labelAlign:"right",
							store:new Ext.data.ArrayStore({
								fields: ['useTypeCode','value'],
								data : [
								['-1',"全部"],['0',"导购"],['1',"主管"],['2',"内衣功能区"],['3',"大场"]
								]
							}),
							valueField: 'useTypeCode',
							displayField:'value',
							hiddenName:'useTypeCode',
							triggerAction : 'all',
							name:'useType',
							mode:'local'
						},*/
						{
							xtype:"combo",
							fieldLabel : '门店',
							labelWidth: 30,
							labelAlign:"right",
							width : 150,
							id:"padShopIdOfPAV",
							store : shopStore,
							valueField: 'sid',
							displayField:'shopName',
							disabled:userAuthId!='1000'?true:false,
							hiddenName:'sid',
							triggerAction : 'all',
							name:'shop',
							mode:'local'
						},{
							xtype:'combo',
							fieldLabel:'目的门店',
							labelWidth: 60,
							labelAlign:"right",
							width : 180,
							id:"padTargetShopIdOfPAV",
							valueField: 'sid',
							store : shopStore,
							displayField:'shopName',
							triggerAction : 'all',
							hiddenName:'targetShop',
							name:'targetShop',
							mode:'local'
								
						},{
							xtype:"combo",
							fieldLabel : '供应商',
							labelWidth: 60,
							labelAlign:"right",
							width :250,
							id:"padSupplySidOfPAV",
							store:new Ext.data.Store({
					        	autoLoad : true,
								proxy : {
									type : 'ajax',
									api : {
										read : _appctx + '/guideSupply/querySupplyInfo',
									},
									reader : {
										type: 'json',
					 					root: 'result'
									}
								},
								fields : ["sid","supplyName"]
					    	}),
							valueField: 'sid',
							displayField:"supplyName",
							hiddenName:'sid',
							triggerAction : 'all',
							name:'sid',
							queryMode: "local",
							typeAhead: true
		              	},
						{
							text:'查找',
							width:80,
							pressed: true,
							ctCls: 'x-btn-over',
							margin : '0 10 0 25',
							handler:function(){
								 padinfoStore.load({
								 	 params:{
								 		shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPAV').getValue(),
										supplyId : Ext.getCmp('padSupplySidOfPAV').getValue(),
										padNo : Ext.getCmp('padNoIdOfPAV').getValue(),
										macAddress : Ext.getCmp('macAddressIdOfPAV').getValue(),
										padStatus : Ext.getCmp('padStatusIdOfPAV').getValue(),
//										useType : Ext.getCmp('useTypeIdOfPAV').getValue(),
									}  
								});  
							}
						},
						{
							text:'调拨',
							width:80,
							pressed: true,
							ctCls: 'x-btn-over',
							margin : '0 5 0 5',
							handler:function(){
								var padIdArr = checkbox.getSelection();
								var padIds = "";
								for(var i=0;i<padIdArr.length;i++){
									padIds = padIds + padIdArr[i].get('padNo') +",";
								}
								if(padIds!=null&&padIds!=""){
									Ext.create("ShopinDesktop.PadAllocatePanel",{
										id:"PadAllocatePanelId",
										padIds:padIds,
									}).show();
								}else{
									Ext.Msg.alert('信息提示','请选择要调拨的Pad!');
								}
								
//								 padinfoStore.load({
//								 	 params:{
//								 		shopId : Ext.getCmp('padShopIdOfPAV').getValue(),
//										supplyId : Ext.getCmp('padSupplySidOfPAV').getValue(),
//										padNo : Ext.getCmp('padNoIdOfPAV').getValue(),
//										macAddress : Ext.getCmp('macAddressIdOfPAV').getValue(),
//										padStatus : Ext.getCmp('padStatusIdOfPAV').getValue(),
//										useType : Ext.getCmp('useTypeIdOfPAV').getValue()
//									}  
//								});
							}
						},{
							//TODO 目的门店为本店&状态在途
							text:'待收货查询',
							width:80,
							pressed: true,
							tooltip: '查询本店内待收货PAD',
							ctCls: 'x-btn-over',
							margin : '0 5 0 5',
							handler:function(view, record,item){
								Ext.create("ShopinDesktop.PadReceiveView",{
		        					id: "PadReceiveViewId",
									baseUrl: _appctx,
									title : 'PAD收货',
								}).show();
							}
						}
					]
				}
			]
		});
		if(userAuthId!='1000'){
			Ext.getCmp("padShopIdOfPAV").setValue(userAuthName);
		}
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padBaseInfoStore,
			displayInfo : true
		});
	}
});
