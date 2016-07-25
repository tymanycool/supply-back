/**
 * 编辑导购基本信息
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideinfoUpdateWindow', {
	extend : 'Ext.window.Window',
	requires : [ 'Ext.tab.Panel','Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	formPanel : null,
	sid :null,
	familyStore : null,
	workStore : null,
	guideNo : null,
	itemsPerPage : 20,
	id: null,
    
	constructor : function(config) {
		config = config || {};
		Ext.apply(this, config);
		var me = this;
		this.initComponents(me);

		this.superclass.constructor.call(this, {
			title :'编辑导购基本信息',
			width : 700,
			height : 610,
			layout:"fit",
			modal: true,
			maximizable:true,
			activeTab : 0,
			bodyStyle : 'padding: 5px;',
			closeAction: 'close', //close 关闭  hide  隐藏  
			items : [this.formPanel],
			buttons :[
				{
					text : '保存',
					id : 'updateSave',
					handler : function() {
						me.update(me);
						Ext.getCmp('updateSave').destroy();
					}
				},{
					text : '取消',
					handler : function() {
						me.cancel();
						Ext.getCmp('updateSave').destroy();
					}
				} 
			],
			listeners: {
				close: function(window, obj) {
					if(Ext.getCmp('updateSave') != undefined){
						Ext.getCmp('updateSave').destroy();
					}
				}
			}
		});
		
		//统计查询查看明细 与 导购管理修改 用的同一个明细页面，统计查询查看明细不显示保存按钮
		var type = this.type;
		if(type == "detail"){
			Ext.getCmp('updateSave').destroy();//destroy()销毁，此处不能用隐藏属性，因为同时打开多个页面id会被占用，保存按钮功能有影响
		}
	},
	initComponents : function(me) {
		
		var record = this.record;
		var sid = record.data.sid;
		var guideNo = record.data.guideNo;
		var name = record.data.name;
		var spell = record.data.spell;
		var mobile = record.data.mobile;
		var email = record.data.email;
		var age = record.data.age;
		var stature = record.data.stature;
		var sex = record.data.sex;
		var address = record.data.address;
		var presentAddress = record.data.presentAddress;
		var education = record.data.education;
		var educationCartNum = record.data.educationCartNum;
		var kitasNum = record.data.kitasNum;
		var kitasEndtime = record.data.kitasEndtime;
		var healthCartNum = record.data.healthCartNum;
		var healthCartEndtime = record.data.healthCartEndtime;
		var chestNum = record.data.chestNum;
		var guideCard = record.data.guideCard;
		var chestBit = record.data.chestBit;
		var depositBit = record.data.depositBit;
		var depositNum = record.data.depositNum;
		var entrytime = record.data.entrytime;
		var leavetime = record.data.leavetime;
		var guideStatus = record.data.guideStatus;
//		var guideBit = record.data.guideBit;
			
  		var chestCombo= new Ext.data.ArrayStore({
			fields: ['chestBitCode','value'],
			data : [
			['0',"未领取"],['1',"临时胸卡"],['2',"正式胸卡"]
			]
		});
  		
  		var guideStatusCombo = new Ext.data.ArrayStore({
			fields: ['guideStatusCode','value'],
			data : [
				['0',"初始状态"],['1',"在柜"],['2',"不在柜"]
			]
		});
  		
  		var depositBitCombo= new Ext.data.ArrayStore({
			fields: ['depositBitCode','value'],
			data : [
				['0',"未交"],['1',"已交"]
			]
		});
  		
//  		var guideBitCombo= new Ext.data.ArrayStore({
//			fields: ['guideBitCode','value'],
//			data : [
//			        ['0',"不是"],['1',"是"]
//			]
//		});
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		this.worksm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		 var checkbox = this.sm;
		
		this.familyStore = Ext.create("Ext.data.Store",{
			autoLoad : true,
			pageSize : this.itemsPerPage,
			fields : [
					"sid","familyName","relation","mobile"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/guideinfo/getFamilyinfoDetail?guideNo='+guideNo,
					getMethod: function(){
						return 'POST';
					},
					reader: {
						type: "json",
						root : "obj.list"
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
		
		this.workStore = Ext.create("Ext.data.Store",{
			autoLoad : true,
			pageSize : this.itemsPerPage,
			fields : [
					"sid","workStarttime","workEndtime","company","position","leaveResult"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/guideinfo/getWorkinfoDetail?guideNo='+guideNo,
					getMethod: function(){
						return 'POST';
					},
					reader: {
						type: "json",
						root : "obj.list"
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
		
		var gridCheckBox = Ext.create('Ext.selection.CheckboxModel',{
					singleSelect : true
				});
		
		//修改可编辑表格显示文字为中文，否则为英文
		if (Ext.grid.RowEditor) {
			Ext.apply(Ext.grid.RowEditor.prototype, {
				saveBtnText: '保存',
				cancelBtnText: '取消',
				errorsText: '错误信息',
				dirtyText: '已修改,你需要提交或取消变更'
			});
		}
		
		var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	        clicksToMoveEditor: 1,
	        autoCancel: false
	    });
		
		//家庭成员信息表格
		var familyInfoStore = Ext.create('Ext.data.JsonStore', {
					autoLoad : false,
					model: 'FamilyInfo1',
					fields : [  {
						name : 'sid',
						type : 'string'
					},{
						name : 'familyName',
						type : 'string'
					}, {
						name : 'relation',
						type : 'string'
					}, {
						name : 'mobile',
						type : 'string'
					}],
					storeId : 'familyInfoStore',
					listeners : {
						beforeload : function(store, operation, eOpts) {
						}
					}
				});
		
		 Ext.define('FamilyInfo1', {
//        extend: 'Ext.data.Model',
	        fields : [  {
						name : 'sid',
						type : 'string'
					},{
						name : 'familyName',
						type : 'string'
					}, {
						name : 'relation',
						type : 'string'
					}, {
						name : 'mobile',
						type : 'string'
					}],
   		 });

		 
		 
		var familyInfoPanel = Ext.create("Ext.grid.Panel",{
				id : 'familyInfoGridPanel',
				columns : [
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{
						dataIndex : 'familyName',
						text :'姓名',
						width:110
					},{
						dataIndex : 'relation',
						text :'关系',
						width:120
					},{
						dataIndex : 'mobile',
						text : '电话',
						width:120
					},{
					text:"操作",
					xtype:'actioncolumn',
					sortable: true,
					items:[{
						text:'删除',
						xtype : 'button',
						tooltip: '删除',
						icon:_appctx+'/images/remove.gif',
						handler:function(grid, rowIndex, colIndex){
							var allsid = checkbox.getSelection();
							if(allsid[0] == undefined){
									Ext.MessageBox.alert("提示","请选中一条数据");
									return;
							}else{
								Ext.MessageBox.confirm('Confirm', '你确定要进行此操作吗？',function (button){
									if(button=="yes"){
										var allsid = checkbox.getSelection();
										var sid  = allsid[0].get('sid');;
										delFamilyinfo(sid);
									}
					             });
							}}
					}]
				}],
				 tbar: [{
		            text: '添加',
		            xtype : 'button',
		            handler : function() {
					 
					 	var win = null;
						var familyInfo = Ext.create('Ext.form.Panel', {
							url : _appctx + '/guideinfo/saveGuideAttach',
							layout : 'anchor',
							bodyPadding: '15 15 15 15',
							region : 'center',
							height : 500,
							defaults : {
								anchor : '100%',
								labelAlign : 'right'
							},
			                items: [{
				                    xtype: 'container',
				                    layout: 'hbox',
				                    items: [
				                    	{
			                    			xtype : 'textfield',
											name : 'guideNo',
											fieldLabel : 'guideNo',
											hidden:true,
										},
										{
			                    			xtype : 'textfield',
											name : 'type',
											fieldLabel : 'type',
											hidden:true,
										},
				                    	{
			                    			xtype: 'textfield',
											name : 'familyName',
											width : 230,
											labelWidth : 50,
											fieldLabel : '姓名'
										}]
										},{
											xtype: 'container',
						                    layout: 'hbox',
						                    height:5
						                },{
						                    xtype: 'container',
						                    layout: 'hbox',
						                    items: [
						                    	{
						                    		xtype: 'textfield',
													name : 'relation',
													width : 230,
													labelWidth : 50,
													fieldLabel : '关系'
												}]
						                },{
											xtype: 'container',
						                    layout: 'hbox',
						                    height:5
						                },{
						                    xtype: 'container',
						                    layout: 'hbox',
						                    items: [
						                    	{
						                    		xtype: 'textfield',
													name : 'mobile',
													width : 230,
													labelWidth : 50,
													fieldLabel : '电话'
												}
						                    ]
						                }
			                			]
								
						});
						win = Ext.create('Ext.window.Window', {
							constrain : true,
							title:"添加家庭成员信息",
							height : 200,
							id:'addFamilyInfoWindowId',
							width  : 320,
							plain : true,
							resizable :true,
							items : [ familyInfo ],
							buttons : [ {
								text : '确定',
								handler : function() {
								
									if (familyInfo.getForm().isValid()) {
										familyInfo.form.findField('guideNo').setValue(guideNo);
										familyInfo.form.findField('type').setValue("1");
										familyInfo.getForm().submit({
											success : function(form, action) {
												win.close();
												Ext.getCmp('familyInfoGridPanel').getStore().load();
												Ext.Msg.alert('','增加成功');
											},
											failure : function(form, action) {
												Ext.create('Ext.ux.window.Notification', {
													position: 'tr',
													useXAxis: true,
													cls: 'ux-notification-light',
													iconCls: 'ux-notification-icon-error',
													closable: true,
													title: '',
													html: '新增失败，请稍后重试！',
													slideInDuration: 800,
													slideBackDuration: 1500,
													autoCloseDelay: 4000,
													slideInAnimation: 'elasticIn',
													slideBackAnimation: 'elasticIn'
												}).show();
											}
										});
									}
								}
							}, {
								text : '取消',
								handler : function() {
									win.close();
								}
							} ]
					});
					Ext.getCmp(me.id).add(win);
					win.show();
					 
						//动态添加一行
//		                rowEditing.cancelEdit();
//		
//		                // Create a model instance
//		                var r = Ext.create('FamilyInfo1', {
//		                    name: '',
//		                    relation: '',
//		                    mobile: ''
//		                });
//		
//		                familyInfoStore.insert(0, r);
//					 
//		                rowEditing.startEdit(0, 0);
					 
		            }
		        }],
		        
				store :this.familyStore,
				height:130,
				columnLines : true,
				autoscroll: true,
				selModel: this.sm
//				,
//				selModel: [Ext.create('Ext.selection.CheckboxModel',{
//					singleSelect : true
//				})]
			});
		
		/**
		 * 删除
		 * @param {Object} sid
		 */
		function delFamilyinfo(sid){
			Ext.Ajax.request({
					url : _appctx + '/guideinfo/delGuideAttachInfo',
					method:'POST',
					params: { sid: sid},
					success: function(response){
						var result = Ext.decode(response.responseText);
						if(result.success=="true"){
							Ext.Msg.alert('','删除成功');
							Ext.getCmp('familyInfoGridPanel').getStore().load();
						}else{
							Ext.Msg.alert('错误','删除失败！'+result.msg);
						}
					},
					failure: function(){
						Ext.Msg.alert('','删除失败，请与管理员联系');
					}
				})
		 }
		
		//工作经历信息表格
		var workExperienceStore = Ext.create('Ext.data.JsonStore', {
					autoLoad : false,
					fields : [  {
						name : 'workStarttime'
					}, {
						name : 'workEndtime'
					}, {
						name : 'company',
						type : 'string'
					},{
						name : 'position',
						type : 'string'
					},{
						name : 'leaveResult',
						type : 'string'
					}],
					storeId : 'workExperienceStore',
					listeners : {
						beforeload : function(store, operation, eOpts) {
							
						}
					}
				});
		
		Ext.define('WorkExperience', {
//        extend: 'Ext.data.Model',
	        fields : [  {
						name : 'workStarttime',
						type : 'date',
						dateFormat: 'Y-m-d H:i:s' 
					}, {
						name : 'workEndtime',
						type : 'date',
						dateFormat: 'Y-m-d H:i:s' 
					}, {
						name : 'company',
						type : 'string'
					},{
						name : 'position',
						type : 'string'
					},{
						name : 'leaveResult',
						type : 'string'
					}],
   		 });
		
		var workExperiencePanel = Ext.create("Ext.grid.Panel",{
				id : 'workExperiencePanel',
//				plugins: [
//			         Ext.create('Ext.grid.plugin.RowEditing', {
//					        clicksToMoveEditor: 1,
//					        autoCancel: false
//					    })
//				      ],
				columns : [ 
//					new Ext.grid.RowNumberer({
//							width:40
//						}),
					{
						dataIndex : 'workStarttime',
//						xtype: 'datecolumn',
						text :'开始时间',
						format:'Y-m-d H:i:s',
						width:110
					},{
						dataIndex : 'workEndtime',
//						xtype: 'datecolumn',
						format:'Y-m-d H:i:s',
						text :'截止时间',
						width:120
					},{
						dataIndex : 'company',
						text : '工作单位',
						width:60
					},{
						dataIndex : 'position',
						text :'职务',
						width:60
					},{
						dataIndex : 'leaveResult',
						text : '离职原因',
						width:150
					}
//					,
//					{
//					text:"操作",
//					xtype:'actioncolumn',
//					sortable: true,
//					items:[{
//						text:'删除',
//						xtype : 'button',
//						tooltip: '删除',
//						icon:_appctx+'/images/remove.gif',
//						handler:function(grid, rowIndex, colIndex){
//							Ext.MessageBox.confirm('Confirm', '你确定要进行此操作吗？',function (e){
//								
//								if('yes'==e){
//									var sm = grid.getSelectionModel();
//					                rowEditing.cancelEdit();
//					                workExperienceStore.remove(sm.getSelection());
//					                if (workExperienceStore.getCount() > 0) {
//					                    sm.select(0);
//					                }
//								}
//							});
//	
//						}
//					}]
//				}
					],
//				tbar: [{
//		            text: '添加',
//					xtype : 'button',
//		            handler : function() {
//					
//						var win = null;
//						var workInfoPanel = Ext.create('Ext.form.Panel', {
//							url : _appctx + '/guideinfo/saveGuideAttach',
//							layout : 'anchor',
//							bodyPadding: '15 15 15 15',
//							region : 'center',
//							height : 500,
//							defaults : {
//								anchor : '100%',
//								labelAlign : 'right'
//							},
//			                items: [{
//				                    xtype: 'container',
//				                    layout: 'hbox',
//				                    items: [
//				                    	{
//			                    			xtype : 'textfield',
//											name : 'guideNo',
//											fieldLabel : 'guideNo',
//											hidden:true,
//										},
//										{
//			                    			xtype : 'textfield',
//											name : 'type',
//											fieldLabel : 'type',
//											hidden:true,
//										},
//				                    	{
//											xtype: 'datefield',
//											name : 'workStarttime',
//											labelWidth:100,
//									  		labelAlign:"right",
//											width : 300,
//											fieldLabel : '开始时间',
//											format:'Y-m-d H:i:s'
//											
//										}]
//										},{
//											xtype: 'container',
//						                    layout: 'hbox',
//						                    height:5
//						                },{
//						                    xtype: 'container',
//						                    layout: 'hbox',
//						                    items: [
//						                    	{
//													
//													xtype: 'datefield',
//													name : 'workEndtime',
//													labelWidth:100,
//											  		labelAlign:"right",
//													width : 300,
//													fieldLabel : '截止时间',
//													format:'Y-m-d H:i:s'
//												}]
//						                },{
//											xtype: 'container',
//						                    layout: 'hbox',
//						                    height:5
//						                },{
//						                    xtype: 'container',
//						                    layout: 'hbox',
//						                    items: [
//												{
//					                    			xtype: 'textfield',
//													name : 'company',
//													width : 230,
//													labelWidth : 50,
//													fieldLabel : '工作单位'
//												}
//						                    ]
//						                },{
//											xtype: 'container',
//						                    layout: 'hbox',
//						                    height:5
//						                },{
//						                    xtype: 'container',
//						                    layout: 'hbox',
//						                    items: [
//						                    	{
//													xtype: 'textfield',
//													name : 'position',
//													width : 230,
//													labelWidth : 50,
//													fieldLabel : '职务'
//												}
//						                    ]
//						                },{
//											xtype: 'container',
//						                    layout: 'hbox',
//						                    height:5
//						                },{
//						                    xtype: 'container',
//						                    layout: 'hbox',
//						                    items: [
//						                    	{
//													xtype: 'textarea ',
//													name : 'leaveResult',
//													width : 230,
//													labelWidth : 50,
//													fieldLabel : '离职原因'
//												}
//						                    ]
//						                }
//			                			]
//								
//						});
//						win = Ext.create('Ext.window.Window', {
//							constrain : true,
//							title:"添加工作经历信息",
//							height : 200,
//							id:'addWorkInfoWindowId',
//							width  : 320,
//							plain : true,
//							resizable :true,
//							items : [ workInfoPanel ],
//							buttons : [ {
//								text : '确定',
//								handler : function() {
//								
//									if (workInfoPanel.getForm().isValid()) {
//										this.workInfoPanel.form.findField('guideNo').setValue(guideNo);
//										this.workInfoPanel.form.findField('type').setValue("2");
//										workInfoPanel.getForm().submit({
//											success : function(form, action) {
//												win.close();
//												Ext.getCmp('workExperiencePanel').getStore().load();
//												Ext.Msg.alert('','增加成功');
//											},
//											failure : function(form, action) {
//												Ext.create('Ext.ux.window.Notification', {
//													position: 'tr',
//													useXAxis: true,
//													cls: 'ux-notification-light',
//													iconCls: 'ux-notification-icon-error',
//													closable: true,
//													title: '',
//													html: '新增失败，请稍后重试！',
//													slideInDuration: 800,
//													slideBackDuration: 1500,
//													autoCloseDelay: 4000,
//													slideInAnimation: 'elasticIn',
//													slideBackAnimation: 'elasticIn'
//												}).show();
//											}
//										});
//									}
//								}
//							}, {
//								text : '取消',
//								handler : function() {
//									win.close();
//								}
//							} ]
//					});
//					Ext.getCmp('guideinfoUpdateId').add(win);
//					win.show();
//					
////		                rowEditing.cancelEdit();
//		
//		                // Create a model instance
////		                var work = Ext.create('WorkExperience', {
////		                    workStarttime: Ext.Date.clearTime(new Date()),
////		                    workEndtime: '',
////		                    company: '',
////		                    position: '',
////		                    leaveResult: ''
////		                });
////		
////		                workExperienceStore.insert(0, work);
////		                rowEditing.startEdit(0, 0);
//		            }
//		        }],
				store :this.workStore,
				height:130,
				columnLines : true,
				autoscroll: true,
				selModel: this.worksm
			});
		
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		
		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/guideinfo/save',
				defaults : {
					anchor : '100%',
					labelAlign : 'right'
				},
				layout : 'anchor',
				autoscroll: true,
				bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
//				height : 550,
				autoScroll:true,
				autoWidth:true,
				manageHeight:true,
//				width : 690,
				bodyPadding: "15 15 15 15",
//				closeAction: 'close', //close 关闭  hide  隐藏  
//				id : 'formPanel',
				items: [ 
					{
			                xtype: 'fieldset',
			                title: "<span style='font-weight:bold;'>基本信息</span>",
			                border: 1,
			                defaultType: 'textfield',
			                layout: 'anchor',
			                defaults: {
			                    anchor: '100%'
			                },
			                width : 630,
			                items: [{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype : 'textfield',
										name : 'familyObj',
										fieldLabel : 'familyObj',
										hidden:true
									},
									{
		                    			xtype : 'textfield',
										name : 'workObj',
										fieldLabel : 'workObj',
										hidden:true
									},
									{
		                    			xtype : 'textfield',
										name : 'username',
										hidden:true,
										value : username
									},
									{
			                    		xtype : 'textfield',
										name : 'userSid',
										value : userSid,
										hidden:true
									},
			                    	{
		                    			xtype : 'textfield',
										name : 'sid',
										hidden:true,
										value : sid
									},
			                    	{
		                    			xtype: 'textfield',
										name : 'name',
										width : 300,
										fieldLabel : '姓名',
										afterLabelTextTpl: required,
										allowBlank : false,
										value: name
									}, {
		                    			xtype: 'textfield',
										name : 'spell',
//										id : 'spell',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '姓名拼音',
										afterLabelTextTpl: required,
										allowBlank : false,
										value: spell
									}]
			                },{xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
										fieldLabel:"性别",
										afterLabelTextTpl: required,
										xtype:"combo",
										width : 300,
										editable:false,
//										id:"sex",
										store:new Ext.data.ArrayStore({
											fields: ['sexCode','value'],
											data : [
											['0',"男"],['1',"女"]
											]
										}),
										valueField: 'value',
										displayField:'value',
										hiddenName:'value',
										triggerAction : 'all',
										name:'sex',
										mode:'local',
										allowBlank : false,
										value: sex
									}, {
		                    			xtype: 'textfield',
										name : 'age',
//										id : 'age',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '年龄',
										afterLabelTextTpl: required,
										allowBlank : false,
										value: age
									}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
		                    			xtype: 'textfield',
										name : 'stature',
//										id : 'stature',
										width : 300,
										fieldLabel : '身高',
										afterLabelTextTpl: required,
										allowBlank : false,
										value : stature
									},{
		                    			xtype: 'textfield',
										name : 'mobile',
//										id : 'mobile',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '联系电话',
										afterLabelTextTpl: required,
										allowBlank : false,
										value : mobile,
										//add by qutengfei for 电话校验 in 20150720 start
										/**
										* 电话号码校验
										* for bug
										* feature https://tower.im/s/57Cdj
										* author qutengfei
										*/
										//regex:/^1(3\d|5[012356789]|8[03456789])\d{8}$/,
										regex:/^1\d{10}$/,
										allowBlank : false,
										regexText:"请输入11位正确的电话号码！"
										//add by qutengfei for 电话校验 in 20150720 end
									}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [ {
		                    			xtype: 'textfield',
										name : 'address',
//										id : 'address',
										width : 300,
										fieldLabel : '家庭住址',
										afterLabelTextTpl: required,
										allowBlank : false,
										value : address
									}, {
		                    			xtype: 'textfield',
										name : 'presentAddress',
//										id : 'presentAddress',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '现住址',
										afterLabelTextTpl: required,
										allowBlank : false,
										value : presentAddress
									}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
		                    			xtype: 'textfield',
										name : 'email',
//										id : 'email',
										width : 300,
										fieldLabel : '邮箱',
										value : email
									},
			                    	{
										fieldLabel:"学历",
										afterLabelTextTpl: required,
										xtype:"combo",
										width : 300,
										store:new Ext.data.ArrayStore({
											fields: ['educationCode','value'],
											data : [
											['0',"本科"],['1',"大本"],['2',"大学"],['3',"大专"],['4',"高中"],['5',"职高"],['6',"中专"]
											]
										}),
										valueField: 'value',
										displayField:'value',
										editable:false,
										hiddenName:'value',
										labelWidth:100,
										labelAlign:"right",
										triggerAction : 'all',
										name:'education',
										mode:'local',
										value : education
									}
			                    ]
			                }]
			            },
			            {
			                xtype: 'fieldset',
			                title: "<span style='font-weight:bold;'>证件信息</span>",
			                border: 1,
			                defaultType: 'textfield',
			                layout: 'anchor',
			                defaults: {
			                    anchor: '100%'
			                },
			                width : 640,
			                items: [{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'guideCard',
//										id : 'guideCard',
										width : 300,
										fieldLabel : '身份证号',
										afterLabelTextTpl: required,
										allowBlank : false,
										value : guideCard
									},
									{	
		                    			xtype: 'textfield',
										name : 'educationCartNum',
//										id : 'educationCartNum',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '学历证编号',
										value :educationCartNum
									}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
									{
		                    			xtype: 'textfield',
										name : 'kitasNum',
//										id : 'kitasNum',
										width : 300,
										fieldLabel : '暂住证编号',
										value : kitasNum
									},{
		                    			xtype: 'datefield',
										name : 'kitasEndtime',
//										id : 'kitasEndtime',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '暂住证有效时间至',
										format:'Y-m-d H:i:s',
										value : kitasEndtime
									}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [ 
									{
		                    			xtype: 'textfield',
										name : 'healthCartNum',
//										id : 'healthCartNum',
										width : 300,
										fieldLabel : '健康证编号',
										value : healthCartNum
									},{
		                    			xtype: 'datefield',
										name : 'healthCartEndtime',
//										id : 'healthCartEndtime',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '健康证有效时间至',
										format:'Y-m-d H:i:s',
										value : healthCartEndtime
									}]
			                }]
			            },
			            {
			                xtype: 'fieldset',
			                title: "<span style='font-weight:bold;'>可联络的其他家庭成员信息</span><span style='font-weight:bold;color:red'>(至少填写一条*)</span>",
			                border: 1,
			                layout: 'anchor',
			                defaults: {
			                    anchor: '100%'
			                },
			                width : 640,
			                items: [
			                	familyInfoPanel
			                	]
			            }
			            ,
			            {
			                xtype: 'fieldset',
			                title: "<span style='font-weight:bold;'>工作经历</span>",
			                border: 1,
			                layout: 'anchor',
			                defaults: {
			                    anchor: '100%'
			                },
			                width : 640,
			                items: [
			                	workExperiencePanel
			                	]
			            },
			             {
			                xtype: 'fieldset',
			                title: "<span style='font-weight:bold;'>文员补充信息</span>",
			                border: 1,
			                defaultType: 'textfield',
			                layout: 'anchor',
			                defaults: {
			                    anchor: '100%'
			                },
			                width : 640,
			                items: [{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
										fieldLabel:"领取胸卡",
										xtype:"combo",
										width : 300,
										store : chestCombo,
										valueField: 'chestBitCode',
										displayField:'value',
										hiddenName:'chestBitCode',
										triggerAction : 'all',
										editable:false,
										name:'chestBit',
										mode:'local'
									},
									{
										fieldLabel:"导购状态",
										xtype:"combo",
										width : 300,
										store: guideStatusCombo,
										valueField: 'guideStatusCode',
										displayField:'value',
										hiddenName:'guideStatusCode',
										editable:false,
										triggerAction : 'all',
										name:'guideStatus',
										mode:'local'
									}
			                    	]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
										fieldLabel:"是否已交押金",
										xtype:"combo",
										width : 300,
//										id:"depositBit",
										store: depositBitCombo,
										valueField: 'depositBitCode',
										displayField:'value',
										hiddenName:'depositBitCode',
										editable:false,
										triggerAction : 'all',
										name:'depositBit',
										mode:'local'
									},
									{
										xtype: 'textfield',
										name : 'depositNum',
//										id : 'depositNum',
										width : 300,
										fieldLabel : '押金单据编号',
										value : depositNum
									}
									]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [ 
									{
										xtype: 'datefield',
										name : 'entrytime',
//										id : 'entrytime',
										labelWidth:100,
										width : 300,
										fieldLabel : '办卡时间',
										editable:false,
										format:'Y-m-d H:i:s',
										value : entrytime
									},{
										xtype: 'datefield',
										name : 'leavetime',
//										id : 'leavetime',
										labelWidth:100,
										width : 300,
										editable:false,
										fieldLabel : '退卡时间',
										format:'Y-m-d H:i:s',
										value : leavetime
									}]
			                }
//			                ,{
//			                	xtype: 'container',
//			                    layout: 'hbox',
//			                    height:5
//			                },
//			                {
//			                    xtype: 'container',
//			                    layout: 'hbox',
//			                    items: [
//			                    	{
//										fieldLabel:"是否是导购",
//										xtype:"combo",
//										width : 300,
//										store : guideBitCombo,
//										valueField: 'guideBitCode',
//										displayField:'value',
//										hiddenName:'guideBitCode',
//										triggerAction : 'all',
//										editable:false,
//										name:'guideBit',
//										mode:'local'
//									}
//			                    ]
//			                }
			                ]
			}
			]});
		
		this.formPanel.form.findField('chestBit').select(chestCombo.getAt(chestBit));
		this.formPanel.form.findField('guideStatus').select(guideStatusCombo.getAt(guideStatus));
		this.formPanel.form.findField('depositBit').select(depositBitCombo.getAt(depositBit));
//		this.formPanel.form.findField('guideBit').select(guideBitCombo.getAt(guideBit));
	},

	cancel : function() {
		this.close();
	},
	update : function(me){
		
			var validBit = this.record.data.validBit;
			if(validBit == 0){
				Ext.Msg.alert("提示","此导购已是无效状态，不能修改信息！");
			    return;
			}
			if (this.formPanel.getForm().isValid()) {

				this.formPanel.getForm().submit({
					success : function(form, action) {
						Ext.MessageBox.alert('提示', action.result.obj);  
						Ext.getCmp(me.id).close();
						Ext.getCmp("guideInfoGridPanel").store.reload();
					},
					failure: function(form, action) {                                          
                        Ext.MessageBox.alert('提示', action.result.obj);  
                    }
				});
			}
	}
	
});
