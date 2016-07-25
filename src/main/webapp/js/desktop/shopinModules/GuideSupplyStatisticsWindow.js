/**
 * 按供应商查询导购
 * @param {Object} config
 * @memberOf {TypeName}
 */
Ext.define('ShopinDesktop.GuideSupplyStatisticsWindow', {
	extend:"Ext.panel.Panel",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	id : 'guideSupplyStatisticsWindow',
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		var me = this;
		this.initComponents();
		this.superclass.constructor.call(this, {
			columnLines: true,
			rowLines: true,
			title: "按供应商统计查询",
			viewConfig: {
				//启用文字选择
				enableTextSelection: true
			},
			layout: "border",
			items: [
				Ext.create("Ext.panel.Panel", {
					border: false,
					region:"north",
					height: "100%",
					layout : 'anchor',
					items: [
						me.gridPanel
					]
				})
			]
		});
	},
	initComponents: function(me) {
		
		var thisView = this;
		var baseUrl = this.baseUrl;

		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;

		this.seleteGuideInforStore = Ext.create("Ext.data.Store", {
			autoLoad: true,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
					"sid","guideNo","chestNum","name","age","stature","address","presentAddress","education","educationCartNum","kitasNum",
					"kitasEndtime","healthCartNum","healthCartEndtime","spell","sex","mobile","email","guideCard","guideBit","chestBit",
					"depositBit","depositNum","entrytime","leavetime","validBit","operator","createtime","guideStatus","supplyName","brand",
					"categroys","supplyId","shopName","changeSupplyId","changeSupplyName","changeBrand","changeCategroys","changeSupplyBit",
					"flag","endTime","chestcardNumber"
					],
			proxy: {
				type: "ajax",
				url : _appctx + '/guideinfo/getAllGuideInfoListBySupply.json',
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
			listeners: {
				beforeload: function(store, operation, eOpts) {
					Ext.apply(store.proxy.extraParams, {
							shopId : Ext.getCmp('statisticShopId').getValue(),
				         	supplyId : Ext.getCmp('suplyStatisticsSid').getValue(),
				         	brandId : Ext.getCmp('supplyBrandSid').getValue(),
				         	guideStatusId : Ext.getCmp('supplyGuideStatusId').getValue(),
				         	validBit : Ext.getCmp('supplyValidBit').getValue(),
							name : Ext.getCmp('guideNameStatisId').getValue(),
							spell : Ext.getCmp('spellStatisId').getValue(),
							flag : Ext.getCmp('supplyflag').getValue()
				        });
				},
				load: function(store, records, success, eOpts) {
				}
			}
		});

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

		this.guidesupplycolumns = [
				new Ext.grid.RowNumberer(),
			 	{
					dataIndex : 'sid',
					hidden : true,
					hideable : false
				}, 
				{header:'导购编号',dataIndex:'guideNo',hidden : true,hideable : false},
				{header:'姓名',dataIndex:'name',width:80,sortable:true},
				{header:'门店',dataIndex:'shopName',width:80,sortable:true},
				{header:'登陆码',dataIndex:'chestcardNumber',width:80,sortable:true},
				{header:'是否转场',dataIndex:'changeSupplyBit',width:60,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '否';
						}
						if(value == 1) {
							return '是';
						}
					}
				},
				
				{header:'供应商编码',dataIndex:'supplyId',width:70,sortable:true},
				{header:'供应商',dataIndex:'supplyName',width:200,sortable:true},
				{header:'品牌',dataIndex:'brand',width:100,sortable:true},
				{header:'品类',dataIndex:'categroys',width:60,sortable:true},
				/*{header:'转场后供应商编码',dataIndex:'changeSupplyId',width:110,sortable:true},
				{header:'转场后供应商',dataIndex:'changeSupplyName',width:200,sortable:true},
				{header:'转场后品牌',dataIndex:'changeBrand',width:100,sortable:true},
				{header:'转场后品类',dataIndex:'changeCategroys',width:80,sortable:true},*/
				{header:'手动变价权限',dataIndex:'flag',width:80,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '否';
						}
						if(value == 1) {
							return '是';
						}
					}
				},
				{header:'权限截止时间',dataIndex:'endTime',width:140,sortable:true},
				{header:'性别',dataIndex:'sex',width:40,sortable:true},
				{header:'手机号码',dataIndex:'mobile',width:90,sortable:true},
				{header:'身份证号',dataIndex:'guideCard',width:150,sortable:true},
				{header:'是否是导购',dataIndex:'guideBit',width:70,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '不是';
						}
						if(value == 1) {
							return '是';
						}
					}
				},
				{header:'导购状态',dataIndex:'guideStatus',width:60,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '初始状态';
						}
						if(value == 1) {
							return '在柜';
						}
						if(value == 2) {
							return '不在柜';
						}
					}
				},
				{header:'是否有效',dataIndex:'validBit',width:60,sortable:true,
					renderer:function(value){
						if(value == 0) {
							return '无效';
						}
						if(value == 1) {
							return '有效';
						}
					}
				},
				{header:'创建时间',dataIndex:'createtime',width:140,sortable:true},
				{
					text:"查看详细信息",
					xtype:'actioncolumn',
					sortable: true,
					width:100,
					align:'center',
					items :[
						{
						text : '查看详细信息',
						xtype : 'button',
						tooltip: '查看详细信息',
						icon:_appctx+'/images/edit.gif',
						handler:function(grid, rowIndex, colIndex){
							var record = grid.getStore().getAt(rowIndex); 
						
							 Ext.create('ShopinDesktop.GuideinfoUpdateWindow',{
							  	record : record,
							  	type : 'detail'
							}).show();
						}
					}
				]	
			}
			]
			
		var guideStatusCombo= new Ext.data.ArrayStore({
			fields: ['guideStatusCode','value'],
			data : [
				['-1',"全部"],['0',"初始状态"],['1',"在柜"],['2',"不在柜"]
			]
		});
		
		var validBitCombo= new Ext.data.ArrayStore({
			fields: ['validBitCode','value'],
			data : [
			['-1',"全部"],['0',"无效"],['1',"有效"]
			]
		});
		
		var flagCombo= new Ext.data.ArrayStore({
			fields: ['flagCode','value'],
			data : [
			        ['-1',"全部"],['0',"否"],['1',"是"]
			        ]
		});
		
		var  infoStroe= this.seleteGuideInforStore;

		this.gridPanel = Ext.create('Ext.grid.Panel', {
	      	id : 'guideSupplyPanel',
      		border:false,
      		enableTabScroll : true,
			autoScroll : true,
			anchor: "100% 100%",
			loadMask:{msg : '数据加载中...'},
			bbar : {
				xtype : 'pagingtoolbar',
				store : this.seleteGuideInforStore,
				displayInfo : true
			},
			columns :this.guidesupplycolumns,
			store: this.seleteGuideInforStore,
//			selModel: this.sm,
			tbar :[
				{
				xtype: "buttongroup",
				width:"100%",
				columns: 5,
				items:[
					{
						xtype : "textfield",
						id:"guideNameStatisId",
						fieldLabel:"导购姓名",
						labelAlign:"right",
						labelWidth: 70,
						width : 140,
					},{
						xtype : "textfield",
						id:"spellStatisId",
						fieldLabel:"姓名拼音",
						labelAlign:"right",
						labelWidth: 60,
						width : 140,
					},
					{
						xtype:"combo",
						fieldLabel : '门店',
						labelWidth: 30,
						labelAlign:"right",
						width : 150,
						id:"statisticShopId",
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
						id:"suplyStatisticsSid",
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
						xtype:"combo",
						fieldLabel : '品牌',
						labelWidth: 40,
						width : 200,
						labelAlign:"right",
						id:"supplyBrandSid",
						store:new Ext.data.JsonStore({
							autoLoad:true,
							proxy : {
								type : 'ajax',
								api : {
									read : _appctx + '/guideSupply/brandList',
								},
								reader : {
									type: 'json',
 									root: 'result'
								}
							},
							fields : ["brandName","brandSid"]
						}),
						valueField: 'brandSid',
						displayField:'brandName',
						hiddenName:'brandSid',
						triggerAction : 'all',
						name:'brandName',
						mode:'local'
					},{
						fieldLabel:"是否有效",
						xtype:"combo",
						labelWidth: 70,
						width : 140,
						labelAlign:"right",
						id:"supplyValidBit",
						store : validBitCombo,
						valueField: 'validBitCode',
						displayField:'value',
						hiddenName:'validBitCode',
						triggerAction : 'all',
						name:'validBit',
						mode:'local'
					},
					{
						fieldLabel:"导购状态",
						xtype:"combo",
						id : "supplyGuideStatusId",
						labelWidth: 60,
						width : 140,
						margin:"0 0 0 0",
						labelAlign:"right",
						store: guideStatusCombo,
						valueField: 'guideStatusCode',
						displayField:'value',
						hiddenName:'guideStatusCode',
						triggerAction : 'all',
						name:'guideStatus',
						mode:'local'
					},
					{
						fieldLabel:"手动变价权限",
						xtype:"combo",
						labelWidth: 80,
						width : 150,
						labelAlign:"right",
						id:"supplyflag",
						margin:"0 0 0 0",
						store : flagCombo,
						valueField: 'flagCode',
						displayField:'value',
						hiddenName:'flagCode',
						triggerAction : 'all',
						name:'flag',
						mode:'local'
					},
					{
						xtype: "button",
						icon:baseUrl + "/images/find.png",
						pressed: true,
						text: "查询",
						aligh : "right",
						width:80,
						margin:"0 10 0 50",
						handler: function() {
							infoStroe.load({
								params:{
									shopId : Ext.getCmp('statisticShopId').getValue(),
									supplyId : Ext.getCmp('suplyStatisticsSid').getValue(),
									brandId : Ext.getCmp('supplyBrandSid').getValue(),
									guideStatusId : Ext.getCmp('supplyGuideStatusId').getValue(),
									validBit : Ext.getCmp('supplyValidBit').getValue(),
									name : Ext.getCmp('guideNameStatisId').getValue(),
									spell : Ext.getCmp('spellStatisId').getValue(),
									flag : Ext.getCmp('supplyflag').getValue()
								}
							});
						}
					},
					{
						xtype : 'button',
						text : '导出EXCEL',
						width:70,
						pressed: true,
						ctCls: 'x-btn-over',
						margin : '0 0 0 -100',
						handler : function() {
							var shopId = Ext.getCmp('statisticShopId').getValue();
							var supplyId = Ext.getCmp('suplyStatisticsSid').getValue();
							var brandId = Ext.getCmp('supplyBrandSid').getValue();
							var guideStatusId = Ext.getCmp('supplyGuideStatusId').getValue();
							var validBit = Ext.getCmp('supplyValidBit').getValue();
							var name = Ext.getCmp('guideNameStatisId').getValue();
							var spell = Ext.getCmp('spellStatisId').getValue();
							var flag = Ext.getCmp('supplyflag').getValue();
							
							var appWindow = window.open(_appctx+"/guideinfo/exportGuideinfoBySupplyToExcel.json?shopId="+shopId+
									"&supplyId="+supplyId+"&brandId="+brandId+"&guideStatusId="+guideStatusId+"&validBit="+validBit+
									"&name="+name+"&spell="+spell+"&flag="+flag+"&type=3");
							appWindow.focus();
						}
					}
			]}],
			listeners:{
				itemdblclick:function(view, record,item){
					Ext.create('ShopinDesktop.GuideinfoUpdateWindow',{
					  	record : record,
					  	type : 'detail'
					}).show();
				}
			}
	});
		
		Ext.getCmp('supplyValidBit').select(validBitCombo.getAt(0));
		Ext.getCmp('supplyflag').select(flagCombo.getAt(0));
		Ext.getCmp('supplyGuideStatusId').select(guideStatusCombo.getAt(0));
	}
});
