# Most probably apache licence

require 'buildr/core/project'
require 'buildr/core/common'

module Buildr
  module Eclipse
    module Android
      include Extension

	    NATURE = ['com.android.ide.eclipse.adt.AndroidNature', 'org.eclipse.jdt.core.javanature']
	    CONTAINER = 'com.android.ide.eclipse.adt.ANDROID_FRAMEWORK'
	    BUILDER = ['com.android.ide.eclipse.adt.ResourceManagerBuilder', 
                'com.android.ide.eclipse.adt.PreCompilerBuilder', 
                'org.eclipse.jdt.core.javabuilder', 
                'com.android.ide.eclipse.adt.ApkBuilder']

      after_define do |project|
        eclipse = project.eclipse

        if eclipse.natures.include? :android
          eclipse.natures += NATURE unless eclipse.natures.include? NATURE
          eclipse.classpath_containers += [CONTAINER] unless eclipse.classpath_containers.include? CONTAINER
          eclipse.builders += BUILDER unless eclipse.builders.include? BUILDER
        end
      end

    end
  end
end

module Buildr
  module Eclipse
    include Extension
    class Eclipse
      def external_sources=(var)
        @external_sources = arrayfy(var)
      end

      def external_sources(*values)
        if values.size > 0
          @external_sources ||= []
          @external_sources += values
        else
          @external_sources || (@project.parent ? @project.parent.eclipse.external_sources : [])
        end
      end
    end
  end
end

class Buildr::Project
  include Buildr::Eclipse::Android
end

