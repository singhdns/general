require 'erb'
require_relative 'setups/Place'

template_file = File.open("./src/Name.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/src/" + Config_setup["package_name"] + "/" + Config_setup["Name"] + ".java", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./src/ManageNamesActivity.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/src/" + Config_setup["package_name"] + "/Manage" + Config_setup["Name"] + "sActivity.java", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./res/layout/activity_manage_Names.xml.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/res/layout/activity_manage_" + Config_setup["Name"].downcase + "s.xml", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./src/NamesDataSource.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/src/" + Config_setup["package_name"] + "/" + Config_setup["Name"] + "sDataSource.java", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./src/MySQLiteHelper_head.erb", 'r').read
erb = ERB.new(template_file)
File.open("./tmp/MySQLiteHelper_head.java", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./src/MySQLiteHelper_mid.erb", 'r').read
erb = ERB.new(template_file)
File.open("./tmp/MySQLiteHelper_mid.java", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./src/MySQLiteHelper_end.erb", 'r').read
erb = ERB.new(template_file)
File.open("./tmp/MySQLiteHelper_end.java", 'w+') { |file| file.write(erb.result(binding)) }

system("cat ./tmp/MySQLiteHelper_head.java ./tmp/MySQLiteHelper_mid.java ./tmp/MySQLiteHelper_end.java > " + Config_setup["project_dir"] + "/src/" + Config_setup["package_name"] + "/MySQLiteHelper.java")

template_file = File.open("./src/SetupNamesActivity.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/src/" + Config_setup["package_name"] + "/Setup" + Config_setup["Name"] + "sActivity.java", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./res/layout/activity_setup_Name.xml.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/res/layout/activity_setup_" + Config_setup["Name"].downcase + ".xml", 'w+') { |file| file.write(erb.result(binding)) }

template_file = File.open("./res/layout/simple_list_item_multiple_choice.xml.erb", 'r').read
erb = ERB.new(template_file)
File.open(Config_setup["project_dir"] + "/res/layout/simple_list_item_multiple_choice_" + Config_setup["Name"].downcase + ".xml", 'w+') { |file| file.write(erb.result(binding)) }
