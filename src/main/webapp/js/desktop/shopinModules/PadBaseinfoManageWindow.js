/**
 * PAD基本信息管理
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadBaseinfoManageWindow', {
	extend:"Ext.ux.desktop.Module",
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
	id : 'padBaseinfoManageWindow',
	init : function(){
//		this.launcher = {
//			text: 'PAD信息管理',
//			iconCls:'icon-grid'
//		};
	},
	createWindow : function() {
		var me = this;
		this.initComponents(me);
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if (!win) {
			win = desktop.createWindow({
				id : me.id,
				title : 'PAD基本信息管理',
				width : 1300,
				height : 580,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : [me.gridPanel],
				bbar : {
					xtype : 'pagingtoolbar',
					store : this.padBaseInfoStore,
					displayInfo : true
				}
			});
		}
		return win;
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
					read : _appctx + '/guideSupply/getShopsList',
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
			id : 'padBaseInfoGridId',
			autoLoad : true,
			pageSize : 20,
			fields : [
					"sid","padNo","macAddress","purchaseOrderno","brand","padStatus","createTime","shopName",
					"supplyName","useType","useTypeDesc","operator","brand","shopId"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/padBaseinfo/list',
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
							shopId : Ext.getCmp('padShopId').getValue(),
							supplyId : Ext.getCmp('padSupplySid').getValue(),
							padNo : Ext.getCmp('padNoId').getValue(),
							macAddress : Ext.getCmp('macAddressId').getValue(),
							padStatus : Ext.getCmp('padStatusId').getValue(),
							useType : Ext.getCmp('useTypeId').getValue()
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
		 
		 
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'padBaseinfoGridPanel',
			loadMask:{msg : '数据加载中...'},
			width : 1000,
			height : 580,
			columns : [
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'编号',dataIndex:'padNo',width:120,sortable:true},
					{header:'MAC地址',dataIndex:'macAddress',width:150,sortable:true},
					{header:'品牌',dataIndex:'brand',width:100,sortable:true},
//					{header:'采购订单号',dataIndex:'purchaseOrderno',width:100,sortable:true},
//					{header:'PAD品牌',dataIndex:'brand',width:100,sortable:true},
					{header:'门店',dataIndex:'shopName',width:80,sortable:true},
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
						}
					},
					{header:'使用类型',dataIndex:'useTypeDesc',width:80,sortable:true},
					{header:'操作人',dataIndex:'operator',width:80,sortable:true},
					{header:'创建时间',dataIndex:'createTime',width:150,sortable:true},
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
					/**
					  * 查看pad安装列表
					  * for bug
					  * feature https://tower.im/s/9cCdh
					  * author qutengfei
					  */
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
					//add by qutengfei for 查看pad安装列表 in 20150723 end
				],
			store: this.padBaseInfoStore,  
			columnLines : true,
			selModel: this.sm,
			tbar:[
				{
					xtype: "buttongroup",
					width:"100%",
					columns: 8,
					items:[
						{
							text:'添加',
							width:80,
							pressed: true,
							ctCls: 'x-btn-over',
							handler:function(){
								addPadBaseinfo();
							}
						},
						{
							xtype : "textfield",
							id:"padNoId",
							fieldLabel:"PAD编号",
							labelAlign:"right",
							labelWidth: 60,
							width : 200,
						},
						{
							xtype : "textfield",
							id:"macAddressId",
							fieldLabel:"MAC地址",
							labelAlign:"right",
							labelWidth: 60,
							width : 200,
						},{
							fieldLabel:"状态",
							xtype:"combo",
							labelWidth: 40,
							width : 130,
							labelAlign:"right",
							id:"padStatusId",
							store:new Ext.data.ArrayStore({
								fields: ['padStatusCode','value'],
								data : [
								['-1',"全部"],['0',"在库"],['1',"卖场"],['2',"送修"],['3',"停用"],['4',"丢失"]
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
							width : 130,
							id:"useTypeId",
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
							labelWidth: 30,
							labelAlign:"right",
							width : 150,
							id:"padShopId",
							store : shopStore,
							valueField: 'sid',
							displayField:'shopName',
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
							id:"padSupplySid",
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
								 		shopId : Ext.getCmp('padShopId').getValue(),
										supplyId : Ext.getCmp('padSupplySid').getValue(),
										padNo : Ext.getCmp('padNoId').getValue(),
										macAddress : Ext.getCmp('macAddressId').getValue(),
										padStatus : Ext.getCmp('padStatusId').getValue(),
										useType : Ext.getCmp('useTypeId').getValue()
									}  
								});  
							}
						}
					]
				}
			],
			sortableColumns : false
		});
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padBaseInfoStore,
			displayInfo : true
		});
	}
});
