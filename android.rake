namespace :android do
  desc "checking the environment is setup"
  task :env do
    puts system('android')
  end
end
