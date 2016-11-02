/**
 * 导购信息注册
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideinfoRegisterWindow', {
	extend : 'Ext.ux.desktop.Module',
	requires : [ 'Ext.grid.Panel', 'Ext.data.JsonStore'],
	id : 'guideinfoRegisterWindow',
	sm :null,
	formPanel : null,
	init : function(){
//		this.launcher = {
//			text: '导购信息注册',
//			iconCls:'icon-grid'
//		};
	},
	createWindow : function() {
		var me = this;
		this.initCmps(me);
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if (!win) {
			win = desktop.createWindow({
				id : me.id,
				title : "导购信息注册",
				width : 700,
				height : 610,
//				animCollapse : false,
//				constrainHeader : true,
				layout : 'fit',
				autoscroll: true,
				items : [ this.formPanel],
				buttons : [ 
						{
							text : '提交',
							id : 'guideinfoSave',
							handler : function() {
//								Ext.Msg.show({
//								title:'提示消息',
//								icon: Ext.MessageBox.QUESTION,
//								msg:'请核对填写信息，提交后将无法修改，确认提交?',
//								buttons: Ext.MessageBox.YESNO,
//								fn:function(btn){
//									if(btn=='yes'){
										me.save();
//									}
//								}
//							});
							}
						},{
							text : '取消',
							handler : function() {
								win.close();
							}
						}
					]
			});
		}
		return win;
	},
	
	initCmps : function(me) {
	
		var gridCheckBox = Ext.create('Ext.selection.CheckboxModel',{
					singleSelect : true
				});
		
		var roleRowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			clicksToEdit : 2,
			autoCancel: true
		});
		
		var roleRowEditingwork = Ext.create('Ext.grid.plugin.RowEditing', {
			clicksToEdit : 2,
			autoCancel: true
		});
		
		
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		
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
					fields : [  {
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
		
		 Ext.define('FamilyInfo', {
//        extend: 'Ext.data.Model',
	        fields : [  {
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
				id : 'familyInfoPanel',
				columns : [
					{
						dataIndex : 'familyName',
						text :'姓名',
						width:110,
						editor: {
			                 xtype:'textfield',
			                 allowBlank: false
				        }
					},{
						dataIndex : 'relation',
						text :'关系',
						width:120,
						editor: {
			                 xtype:'textfield',
			                 allowBlank: false
				        }
					},{
						dataIndex : 'mobile',
						text : '电话',
						width:120,
						editor: {
			                 xtype:'textfield',
			                 allowBlank: false
				        }
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
							Ext.MessageBox.confirm('Confirm', '你确定要进行此操作吗？',function (e){
								var sm = grid.getSelectionModel();
				                roleRowEditing.cancelEdit();
				                familyInfoStore.remove(sm.getSelection());
				                if (familyInfoStore.getCount() > 0) {
				                    sm.select(0);
				                }
				             });
						}
					}]
				}],
				 tbar: [{
		            text: '添加',
		            xtype : 'button',
		            handler : function() {
					 
					 	roleRowEditing.cancelEdit();
						familyInfoStore.insert(0, {
							name: '',
		                    relation: '',
		                    mobile: ''
						});
						roleRowEditing.startEdit(0, 1);
		            }
		        }],
		        plugins : [ roleRowEditing ],
				store :familyInfoStore,
				height:130,
				columnLines : true,
				autoscroll: true
			});
		
		//工作经历信息表格
		var workExperienceStore = Ext.create('Ext.data.JsonStore', {
					autoLoad : false,
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
					storeId : 'workExperienceStore',
					listeners : {
						beforeload : function(store, operation, eOpts) {
							
						}
					}
				});
		
		Ext.define('WorkExperience', {
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
				columns : [ 
					{
						dataIndex : 'workStarttime',
						xtype: 'datecolumn',
						text :'开始时间',
						format:'Y-m-d H:i:s',
						width:110,
						editor: {
			                 xtype:'datefield',
			                 editable:false,
			                 allowBlank: false
				        }
					},{
						dataIndex : 'workEndtime',
						xtype: 'datecolumn',
						format:'Y-m-d H:i:s',
						text :'截止时间',
						width:120,
						editor: {
			                 xtype:'datefield',
			                 editable:false,
			                 allowBlank: false
				        }
					},{
						dataIndex : 'company',
						text : '工作单位',
						width:120,
						editor: {
			                 xtype:'textfield'
				        }
					},{
						dataIndex : 'position',
						text :'职务',
						width:120,
						editor: {
			                 xtype:'textfield'
				        }
					},{
						dataIndex : 'leaveResult',
						text : '离职原因',
						width:120,
						editor: {
			                 xtype:'textfield'
				        }
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
							Ext.MessageBox.confirm('Confirm', '你确定要进行此操作吗？',function (e){
								
								if('yes'==e){
									var sm = grid.getSelectionModel();
					                roleRowEditingwork.cancelEdit();
					                workExperienceStore.remove(sm.getSelection());
					                if (workExperienceStore.getCount() > 0) {
					                    sm.select(0);
					                }
								}
							});
	
						}
					}]
				}],
				tbar: [{
		            text: '添加',
		            xtype : 'button',
		            handler : function() {
		                roleRowEditingwork.cancelEdit();
						workExperienceStore.insert(0, {
							workStarttime: '',
		                    workEndtime: '',
		                    company: '',
		                    position: '',
		                    leaveResult: ''
						});
						roleRowEditingwork.startEdit(0, 0);
		            }
		        }],
		        plugins : [ roleRowEditingwork ],
				store :workExperienceStore,
				height:130,
				columnLines : true,
				autoscroll: true
//				,
//				selModel: [Ext.create('Ext.selection.CheckboxModel',{
//					singleSelect : true
//				})]
			});
	
		this.formPanel = Ext.create('Ext.form.Panel', {
				url : _appctx + '/guideinfo/save',
				defaults : {
					anchor : '100%',
					labelAlign : 'right'
				},
				layout : 'anchor',
				autoscroll: true,
				bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
				height : 600,
//				width : 690,
				bodyPadding: "15 15 15 15",
				items: [ {
			                xtype: 'fieldset',
			                title: "<span style='font-weight:bold;'>基本信息</span>",
			                border: 1,
			                defaultType: 'textfield',
			                layout: 'anchor',
			                defaults: {
			                    anchor: '100%'
			                },
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
										hidden:true
									},
			                    	{
		                    			xtype: 'textfield',
										name : 'name',
										width : 300,
										fieldLabel : '姓名',
										afterLabelTextTpl: required,
										allowBlank : false
									}, {
		                    			xtype: 'textfield',
										name : 'spell',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '姓名拼音',
										afterLabelTextTpl: required,
										allowBlank : false
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
										allowBlank : false
									}, {
		                    			xtype: 'textfield',
										name : 'age',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '年龄',
										afterLabelTextTpl: required,
										allowBlank : false
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
										width : 300,
										fieldLabel : '身高',
										afterLabelTextTpl: required,
										allowBlank : false
									},{
		                    			xtype: 'textfield',
										name : 'mobile',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '手机号码',
										afterLabelTextTpl: required,
										//regex: /(^0?[1-9][35][0-9]{9}$)/,
										//add by qutengfei for 电话校验 in 20150720 start
										//regex:/^1(3\d|5[012356789]|8[03456789])\d{8}$/,
										/**
										* 电话号码校验
										* for bug
										* feature https://tower.im/s/57Cdj
										* author qutengfei
										*/
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
										width : 300,
										fieldLabel : '家庭住址',
										afterLabelTextTpl: required,
										allowBlank : false
									}, {
		                    			xtype: 'textfield',
										name : 'presentAddress',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '现住址',
										afterLabelTextTpl: required,
										allowBlank : false
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
										width : 300,
										fieldLabel : '邮箱',
										vtype: 'email'
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
										editable:false,
										displayField:'value',
										hiddenName:'value',
										labelWidth:100,
										labelAlign:"right",
										triggerAction : 'all',
										name:'education',
										mode:'local'
									}]
			                },{
			                	xtype: 'container',
			                    layout: 'hbox',
			                    height:5
			                },{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [{
										fieldLabel:"身份",
										afterLabelTextTpl: required,
										xtype:"combo",
										width : 300,
										editable:false,
										store:new Ext.data.ArrayStore({
											fields: ['identityCode','value'],
											data : [
											['0',"主管"],['1',"导购"],['2',"客服"]
											]
										}),
										valueField: 'identityCode',
										displayField:'value',
										hiddenName:'value',
										triggerAction : 'all',
										name:'guideBit',
										mode:'local',
										allowBlank : false
									}]
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
			                items: [{
			                    xtype: 'container',
			                    layout: 'hbox',
			                    items: [
			                    	{
		                    			xtype: 'textfield',
										name : 'guideCard',
										width : 300,
										fieldLabel : '身份证号',
										afterLabelTextTpl: required,
										regex : /^(\d{18,18}|\d{15,15}|\d{17,17}[x|X])$/,
										allowBlank : false
									},
									{	
		                    			xtype: 'textfield',
										name : 'educationCartNum',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '学历证编号'
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
										width : 300,
										fieldLabel : '暂住证编号'
									},{
		                    			xtype: 'datefield',
										name : 'kitasEndTime',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '暂住证有效时间至',
										editable:false,
										format:'Y-m-d H:i:s'
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
										width : 300,
										fieldLabel : '健康证编号'
									},{
		                    			xtype: 'datefield',
										name : 'healthCartEndTime',
										labelWidth:100,
								  		labelAlign:"right",
										width : 300,
										fieldLabel : '健康证有效时间至',
										editable:false,
										format:'Y-m-d H:i:s'
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
			                items: [
			                	workExperiencePanel
			                	]
			            }
				]
				
					})
	
	},
	
	save : function(){
			if (this.formPanel.getForm().isValid()) {
//				var aa = this.formPanel.getForm();
				
//				Ext.getCmp("guideinfoSave").setDisabled(true);
				var familystore=Ext.getCmp('familyInfoPanel').getStore();
				
				var familyArr= new Array();  
				var jsonfamily = "";
				var strat = "[";
				var end = "]";
                familystore.each(function(record){  
                	jsonfamily = "{familyName:'"+record.get('familyName')+"',relation:'"+record.get('relation')+"',familyMobile:'"+record.get('mobile')+"'}";
                    familyArr.push(jsonfamily);  
                })
                
                var workstore=Ext.getCmp('workExperiencePanel').getStore();
                var worksArr= new Array(); 
                var jsonwork = "[";
                
                workstore.each(function(record){  //position   
                	jsonwork = "{workStarttime:'"+record.get('workStarttime')+"',workEndtime:'"+record.get('workEndtime')+
                				"',company:'"+record.get('company')+"',position:'"+record.get('position')+"',leaveResult:'"+record.get('leaveResult')+"'}";
                    worksArr.push(jsonwork);  
                })
//				var count = store.getCount();
//				for(var i=0;i<count;i++){
////					alert(store.getAt(i).get('familyName'));
//					store.getAt(i).get('familyName');
//				}
				this.formPanel.form.findField('familyObj').setValue(strat+familyArr+end);
				this.formPanel.form.findField('workObj').setValue(strat+worksArr+end);
				var familyObj = this.formPanel.form.findField('familyObj').getValue();
				if(familyObj == "[]" ){
					 Ext.Msg.alert('',"请至少填写一项可联络家庭成员信息！");
				     return;
				}
				
				this.formPanel.form.findField('username').setValue(username);
				
				this.formPanel.getForm().submit({
					success : function(form, action) {
						if(action.result.success == "true"){
							Ext.Msg.alert('',"信息注册成功！");
							Ext.getCmp("guideinfoRegisterWindow").close();
						}else if(action.result.success == "false"){
							Ext.Msg.alert('',"信息注册失败！");
						}
					},
					failure : function(form, action) {
						 Ext.Msg.alert('',"信息注册失败！");
				     	 return
					}
				});
			}
	}
});
