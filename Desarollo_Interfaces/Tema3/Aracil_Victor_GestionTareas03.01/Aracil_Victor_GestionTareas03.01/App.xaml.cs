using System.Configuration;
using System.Data;
using System.Windows;

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    /// <author> Victor Aracil Gozalvez</author>
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);
            RepararPasswordVictor();
        }

        private void RepararPasswordVictor()
        {
            try
            {
                using (var context = new TaskManager.Data.Models.TaskManagerDbContext())
                {
                    var victor = System.Linq.Enumerable.FirstOrDefault(context.Users, u => u.Usuario == "victor");
                    if (victor != null)
                    {
                        // Hash de "1234"
                        victor.PasswordHash = "1234";
                        context.SaveChanges();
                    }
                }
            }
            catch (System.Exception ex)
            {
                MessageBox.Show("Error intentando reparar la contraseña: " + ex.Message);
            }
        }
    }
}
