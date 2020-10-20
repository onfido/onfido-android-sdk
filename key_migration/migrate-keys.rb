#!/usr/bin/env ruby

require 'fileutils'
require 'json'

STRING_ENTRY_REGEX_IOS = /^"(.+)"\s*=\s*"(.+)";$/
STRING_ENTRY_REGEX_ANDROID = /^\s*<string name="(.+)">(.+)<\/string>$/

PLATFORMS = ['ios', 'android']

class Logger

  def self.error(message)
    puts("❌: #{message}")
  end

  def self.success(message)
    puts("✅: #{message}")
  end

  def self.info(message)
    puts("ℹ️: #{message}")
  end

end

class MigrateKeysRunner

  def initialize(files_path, platform, key_mapping_file)
    @files_path = files_path
    @platform = platform
    @key_mapping_file = key_mapping_file
  end

  def run
    file_path_list = retrieve_file_paths_to_update
    Logger.success('Retrieved files to update')

    if file_path_list.empty?
      Logger.info('No files to update')
      return
    end

    keys_to_update = retrieve_keys_to_update
    Logger.success('Retrieved keys to update')
    any_file_updated = false
    file_path_list.each { |file_path|
      any_file_updated = true if update_file(file_path, keys_to_update)
    }
    if any_file_updated
      Logger.success('Updated files')
    else
      Logger.info('Nothing found to update')
    end
  rescue Exception => exception
    Logger.error(exception.message)
  end

  private

  def retrieve_keys_to_update
    file_content = File.read(@key_mapping_file)
    JSON.parse(file_content)
  end

  def retrieve_file_paths_to_update
    Dir.glob("#{@files_path}/**/*.#{string_file_extension}")
  end

  def string_file_extension
    if @platform == 'ios'
      return 'strings'
    elsif @platform == 'android'
      return 'xml'
    end
  end

  def update_file(file, key_dict)
    content = File.read(file)
    content_changed = false
    key_dict.each do | old_key, new_key_arr |
      File.readlines(file).each do |line|
        pair_match = line.match(regex_for_string_file)
        next unless pair_match
        updated_content = handle_key_update(old_key, new_key_arr, line, pair_match, content)
        if updated_content
          content = updated_content
          content_changed = true
        end
      end
    end
    save_changes(file, content) if content_changed
    return content_changed
  end

  def save_changes(file_path, content)
    back_file_path = "#{File.dirname(file_path)}/#{File.basename(file_path)}.backup"
    if File.file?(back_file_path)
      Logger.error("Backup file already exists: #{back_file_path}. Terminating script.")
      exit 1
    end
    FileUtils.cp(file_path, back_file_path)
    IO.write(file_path, content)
  end

  def handle_key_update(old_key, new_key_arr, line, pair_match, content)
    translation_key = pair_match[1]
    if translation_key == old_key
      translation_value = pair_match[2]
      entry = build_string_entry(new_key_arr, translation_value)
      content = content.sub(line, entry)
      print_updated_keys(translation_key, new_key_arr)
      return content
    end
    return nil
  end

  def print_updated_keys(translation_key, keys)
    keys.each { |new_key|
      Logger.success("Updated #{translation_key} with #{new_key}")
    }
  end

  def regex_for_string_file
    if @platform == 'ios'
      return STRING_ENTRY_REGEX_IOS
    elsif @platform == 'android'
      return STRING_ENTRY_REGEX_ANDROID
    end
  end

  def build_string_entry(key_arr, value)
    entry = ''
    if @platform == 'ios'
      key_arr.each { |key|
        entry.concat("\"#{key}\" = \"#{value}\";\n")
      }
    elsif @platform == 'android'
      key_arr.each { |key|
        entry.concat("<string name=\"#{key}\">#{value}</string>\n")
      }
    end
    return entry
  end
end

has_files_path = (ARGV[0] == '--files-path')
unless has_files_path
  Logger.error("--files-path must be specified")
  exit 1
end

files_path = ARGV[1]
has_platform = (ARGV[2] == '--platform')
unless has_platform
  Logger.error("--platform must be specified, options: #{PLATFORMS.join(', ')}")
  exit 1
end

platform = ARGV[3]
unless PLATFORMS.include?(platform)
  Logger.error("The value for platform is not valid, valid options: #{PLATFORMS.join(', ')}")
  exit 1
end

has_key_mapping_file = (ARGV[4] == '--key-mapping-file')
unless has_key_mapping_file
  Logger.error("--key-mapping-file must be specified")
  exit 1
end

key_mapping_file = ARGV[5]

MigrateKeysRunner.new(files_path, platform, key_mapping_file).run
