
/**
 * PAD信息查看
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadInfoCheckView', {
	extend:"Ext.window.Window",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
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
	id : 'PadInfoCheckView1',
	init : function(){

	},
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			width : 1300,
			height : 580,
			modal: true,
			constrain: true,
			layout: "border",
			title: "PAD信息查看",
			items : [this.gridPanel],
			animCollapse : false,
			constrainHeader : true,
			layout : 'fit',
			bbar : {
				xtype : 'pagingtoolbar',
				store : this.padBaseInfoStore,
				displayInfo : true
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;
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
			id : 'padBaseInfoGridIdOfPCV',
			autoLoad : true,
			pageSize : 20,
			fields : [
			         "sid", "padNo",
					 "macAddress",
					 "padPurchaseBatchNo",
					 "purchaseOrderno", "padBrand","padSupply","padPurchaseTime",
					 "padStatus", "createTime",
					 "shopName", "targetShopId",
					 "targetShopName",
					 "supplyName", "useType",
					 "useTypeDesc", "operator",
					 "brand", "shopId"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/padBaseinfo/getPadInfoAndPurchaseInfo',
					getMethod: function(){
						return 'POST';
					},
					reader: {
						type: "json",
						root : "obj.list",
						totalProperty :"total"
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
							shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPCV').getValue(),
							supplyId : Ext.getCmp('padSupplySidOfPCV').getValue(),
							padNo : Ext.getCmp('padNoIdOfPCV').getValue(),
							macAddress : Ext.getCmp('macAddressIdOfPCV').getValue(),
							padStatus : Ext.getCmp('padStatusIdOfPCV').getValue(),
							useType : Ext.getCmp('useTypeIdOfPCV').getValue(),
							padProducer : Ext.getCmp('padProducerOfPCV').getValue(),
							padBrand : Ext.getCmp('padBrandOfPCV').getValue(),
							padPurchaseBatchNo : Ext.getCmp('padPurchaseBatchNoOfPCV').getValue(),
				        });
					},
				load: function(store, records, success, eOpts) {
				}
			}
		});
		
		var padBrandStore = Ext.create('Ext.data.Store',{
			autoLoad:true,
  			fields:['padBrand'],
  			proxy:{
  				type:'ajax',
  				url:baseUrl + '/padPurchaseInfo/getPadPurchaseInfo.json',
  				reader:{
  					type:'json',
  					root:'list'
  				}
  			},
			/*listeners:{
				beforeload: function(store, operation, eOpts) {
					
				}
			}*/
  		});
		
		padBrandStore.load({ 
		    scope : this,
		     add : true
	      });  
		var aa = Ext.create(Ext.ModelManager.getModel(padBrandStore.model),{
			padBrand:'全部'
		});
	  padBrandStore.add([aa]);
	  
		var padSupplyStore = Ext.create("Ext.data.Store",{
			autoLoad:true,
  			fields:['padSupply'],
  			proxy:{
  				type:'ajax',
  				url:baseUrl + '/padPurchaseInfo/getPadPurchaseInfo.json',
  				reader:{
  					type:'json',
  					root:'list'
  				}
  			}
  		});
		var padinfoStore = this.padBaseInfoStore;
		/**
		 * 添加
		 */
		 function addPadBaseinfo(){
			  Ext.create('ShopinDesktop.PadBaseinfoAddWindow',{
				}).show();
		 }
		
		/**
		 * 修改
		 */
		 function updatePadBaseinfo(record){
//			 alert(aa.get("padBrand"));
			  Ext.create('ShopinDesktop.PadInfoUpdateWindowOfPCV',{
				    record : record
//					padNo: padNo,
//					macAddress : macAddress,
//					purchaseOrderno : purchaseOrderno,
//					padStatus : padStatusText
				}).show();
		 }
		 /*
		  * 批量修改
		  */
		 function updatePadBaseinfoBatch(checkboxIds){
			 Ext.create("ShopinDesktop.PadBatchUpdateView",{padNos:checkboxIds}).show();
//			 Ext.create('ShopinDesktop.PadInfoUpdateWindowOfPCV',{
//				    record : record
//					padNo: padNo,
//					macAddress : macAddress,
//					purchaseOrderno : purchaseOrderno,
//					padStatus : padStatusText
//				}).show();
		 }
		 
		 /**
		  * 绑定供应商
		  */
		 function boundSupply(record){
			Ext.create('ShopinDesktop.PadBoundSupplyPanel',{
						   record:record
						}).show();
		 }
		 //add by qutengfei for 查看pad安装列表 in 20150723 start
		/**
		  * 查看pad安装列表
		  * for bug
		  * feature https://tower.im/s/9cCdh
		  */
		 function watchPadSoft(record){
			Ext.create('ShopinDesktop.WatchPadSoftWindow',{
						    macAddress:record.data.macAddress,
						    shopId:record.data.shopId
						}).show();
		 }
		 //add by qutengfei for 查看pad安装列表 in 20150723 end
		 function watchPadHistory(){
				Ext.create('ShopinDesktop.WatchPadHistoryWindow',{
							padNo : padNo
						}).show();
		}
		var userAuthId = this.userAuthId;
		var userAuthName = this.userAuthName;
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'padBaseinfoGridPanelOfPCV',
			loadMask:{msg : '数据加载中...'},
			width : 1000,
			height : 580,
			columns : [
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'编号',dataIndex:'padNo',width:100,sortable:true},
					{header:'批次号',dataIndex:'padPurchaseBatchNo',width:80,sortable:true},
					{header:'MAC地址',dataIndex:'macAddress',width:110,sortable:true},
					{header:'PAD品牌',dataIndex:'padBrand',width:80,sortable:true},
					{header:'PAD厂商',dataIndex:'padSupply',width:80,sortable:true},
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
								return '在途';
							}
						}
					},
					{header:'使用类型',dataIndex:'useTypeDesc',width:70,sortable:true},
					{header:'操作人',dataIndex:'operator',width:80,sortable:true},
					{header:'创建时间',dataIndex:'createTime',width:120,sortable:true},
					{header:'PAD订购时间',dataIndex:'padPurchaseTime',width:120,sortable:true}/*,
					{
						text:"修改",
						xtype:'actioncolumn',
						sortable: true,
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
					//add by qutengfei for 查看pad安装列表 in 20150723 start
					*//**
					  * 查看pad安装列表
					  * for bug
					  * feature https://tower.im/s/9cCdh
					  * author qutengfei
					  *//*
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
					},{
						text:"查看历史记录",
						xtype:'actioncolumn',
						sortable: true,
						width:80,
						align:'center',
						items :[
							{
								text : '查看历史记录',
								xtype : 'button',
								tooltip: '查看历史记录',
								icon:_appctx+'/images/edit.gif',
								handler:function(grid, rowIndex, colIndex){//TODO 查看历史记录
									var record = grid.getStore().getAt(rowIndex);
					  				padNo = record.data.padNo;
					  				watchPadHistory();
								}
							}
						]	
					}*/
					//add by qutengfei for 查看pad安装列表 in 20150723 end
				],
			store: this.padBaseInfoStore,  
			columnLines : true,
			selModel: this.sm,
			tbar:[{
					xtype: "buttongroup",
					width:"100%",
					columns: 6,
					items:[
						{
							text:'添加',
							width:80,
							pressed: true,
							hidden:true,
							ctCls: 'x-btn-over',
							handler:function(){
								addPadBaseinfo();
							}
						},
						{
							xtype : "textfield",
							id:"padNoIdOfPCV",
							fieldLabel:"PAD编号",
							labelAlign:"right",
							labelWidth: 60,
							width : 200,
						},
						{
							xtype : "textfield",
							id:"macAddressIdOfPCV",
							fieldLabel:"MAC地址",
							labelAlign:"right",
							labelWidth: 60,
							width : 200,
						},{
							fieldLabel:"状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态",
							xtype:"combo",
							labelWidth: 60,
							width : 200,
							labelAlign:"right",
							id:"padStatusIdOfPCV",
							store:new Ext.data.ArrayStore({
								fields: ['padStatusCode','value'],
								data : [
								['-1',"全部"],['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"],['5',"在途"]
								]
							}),
							valueField: 'padStatusCode',
							displayField:'value',
							hiddenName:'padStatusCode',
							triggerAction : 'all',
							name:'padStatus',
							mode:'local'
						},
						{
							fieldLabel:"使用类型",
							xtype:"combo",
							labelWidth: 60,
							width : 200,
							id:"useTypeIdOfPCV",
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
						},
						{
							xtype:"combo",
							fieldLabel : '门店',
							labelWidth: 60,
							labelAlign:"right",
							width : 200,
							id:"padShopIdOfPCV",
							store : shopStore,
							valueField: 'sid',
							displayField:'shopName',
							disabled:userAuthId!='1000'?true:false,
							hiddenName:'sid',
							triggerAction : 'all',
							name:'shop',
							mode:'local'
						},
						{
							xtype:"combo",
							fieldLabel : '供应商',
							labelWidth: 60,
							labelAlign:"right",
							width :250,
							id:"padSupplySidOfPCV",
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
		              	},{
		              		xtype:'combo',
		              		fieldLabel: '所属批次',
		              		id:'padPurchaseBatchNoOfPCV',
		              		labelWidth:60,
		              		labelAlign:'right',
		              		triggerAction: 'all',
		              		hiddenName:'padPurchaseBatchNo',
		              		name:'padPurchaseBatchNo',
		              		width:200,
		              	    store: Ext.create("Ext.data.Store",{
		              	    	 fields: ['padPurchaseBatchNo'],
		              	       proxy: {
		              	           type: 'ajax',
		              	           url:  baseUrl + '/padPurchaseInfo/getPadPurchaseInfo.json',
		              	           reader: {
		              	               type: 'json',
		              	               root: 'list'
		              	           }
		              	       },
		              	       autoLoad: true
		              	    }),
		              	    queryMode: 'local',
		              	    displayField: 'padPurchaseBatchNo',
		              	    valueField: 'padPurchaseBatchNo',
		              	    hiddenName:'padPurchaseBatchNo'
		              	},{
		              		xtype:'combo',
		              		fieldLabel:'采购品牌',
		              		id:'padBrandOfPCV',
		              		labelWidth:60,
		              		labelAlign:'right',
		              		width:200,
		              		store:padBrandStore,
		              		hiddenName:'padBrand',
		              		displayField:'padBrand',
		              		valueField:'padBrand',
		              		queryMode:'local',
		              		triggerAction: 'all'
		              		
		              	},{
		              		xtype:'combobox',
		              		fieldLabel:'生产厂商',
		              		id:'padProducerOfPCV',
		              		labelWidth:60,
		              		labelAlign:'right',
		              		width:200,
		              		store:padSupplyStore,
		              		hiddenName:'padSupply',
		              		valueField:'padSupply',
		              		displayField:'padSupply',
		              		queryMode:'local',
		              		triggerAction:'all'
		              	},{
							text:'查找',
							width:100,
							pressed: true,
							ctCls: 'x-btn-over',
							margin : '0 10 0 25',
							handler:function(){
								 padinfoStore.load({  
								 	 params:{
								 		shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPCV').getValue(),
										supplyId : Ext.getCmp('padSupplySidOfPCV').getValue(),
										padNo : Ext.getCmp('padNoIdOfPCV').getValue(),
										macAddress : Ext.getCmp('macAddressIdOfPCV').getValue(),
										padStatus : Ext.getCmp('padStatusIdOfPCV').getValue(),
										useType : Ext.getCmp('useTypeIdOfPCV').getValue(),
										padProducer : Ext.getCmp('padProducerOfPCV').getValue(),
										padBrand : Ext.getCmp('padBrandOfPCV').getValue(),
										padPurchaseBatchNo : Ext.getCmp('padPurchaseBatchNoOfPCV').getValue(),
										isCheck : 1
				
									}
								});  
							}
						},{
							text:'批量修改',
							width:100,
							pressed: true,
							ctCls: 'x-btn-over',
							margin : '0 10 0 25',
							handler:function(){
//								获取当前页面选中的复选框
								var checkboxs = Ext.getCmp("padBaseinfoGridPanelOfPCV").getSelectionModel().selected;
								var count = checkboxs.getCount();
								if(count == 0){
									Ext.MessageBox.alert("提示", "请至少选择一行");
									return ;
								}
								var checkboxIds = new Array();
								checkboxs.each(function(item,index,length){
									checkboxIds.push(item.get("padNo"));
								});
								updatePadBaseinfoBatch(checkboxIds);
							}
						}]
					}],
			sortableColumns : false,
//			 dockedItems : [toolbarTop]
		});
		/**
		 * 两行工具条
		 */
		
		var toolbarTop=Ext.create("Ext.toolbar.Toolbar",{
			 dock : 'top',
			items:[]
/*				}
			]*/
		});
		
/**
 * 双击单元格，跳出菜单：修改，绑定供应商，查看安装列表，查看历史记录
 */
		this.gridPanel.on("celldblclick",function( view, td, cellIndex, record, tr, rowIndex, e, eOpts ){
			e.preventDefault();
			if (rowIndex < 0) { return; } 
			if (cellIndex <= 0) { return; } 
			var contextmenu = Ext.create("Ext.menu.Menu",{
				items:[{
					text:"修改",
					handler:function(){
						updatePadBaseinfo(record);
					}
				},{
					text:"绑定供应商",
					handler:function(){
						//在途：不可以绑定供应商
						if(record.data.padStatus === 5){
							Ext.Msg.alert('提示','在途的PAD不可以绑定供应商');
							return;
						}
						boundSupply(record);
					}
				},{
					text:"查看安装列表",
					handler:function(){
						macAddress = record.data.macAddress;
		  				shopId = record.data.shopId;
						watchPadSoft(record);
					}
				},{
					text:"查看历史记录",
					handler:function(){
						padNo = record.data.padNo;
		  				watchPadHistory(padNo);
					}
				}]
			});
			contextmenu.showAt(e.getXY());
		});
		//TODO
		if(userAuthId!='1000'){
			Ext.getCmp("padShopIdOfPCV").setValue(userAuthName);
		}
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padBaseInfoStore,
			displayInfo : true
		});
	}
});
