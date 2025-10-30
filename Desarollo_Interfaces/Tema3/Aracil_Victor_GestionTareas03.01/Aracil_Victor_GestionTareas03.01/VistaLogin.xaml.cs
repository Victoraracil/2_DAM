using Aracil_Victor_GestionTareas03._01;
using Aracil_Victor_GestionTareas03._01.Migrations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using TaskManager.Data.Models;

namespace Aracil_Victor_GestionTareas03._01
{
    /// <summary>
    /// Lógica de interacción para VistaLogin.xaml
    /// </summary>
    public partial class VistaLogin : Window
    {
        public VistaLogin()
        {
            InitializeComponent();
        }

        private void Window_MouseDown(object sender, MouseButtonEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                DragMove();
            }
        }

        private void btn_Minimizar_Click(object sender, RoutedEventArgs e)
        {
            WindowState = WindowState.Minimized;
        }

        private void btn_Close_Click(object sender, RoutedEventArgs e)
        {
            Application.Current.Shutdown();
        }

        private void btn_Login_Click(object sender, RoutedEventArgs e)
        {
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.EnsureAdminUserExists();
            var usuario = txtUser.Text;
            var password = txtPass.Password;
            var passwordHash = PasswordHelper.HashPassword(password);
            using (var context = new TaskManagerDbContext())
            {
                var admin = context.Users
                    .FirstOrDefault(u => u.Usuario == usuario && u.PasswordHash == passwordHash);

                if (admin != null)
                {
                    MessageBox.Show("¡Inicio de sesión exitoso!", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
                    var mainWindow = new MainWindow();
                    mainWindow.Show();
                    this.Close();
                }
                else
                {
                    MessageBox.Show("Usuario o contraseña incorrectos.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }
    }
}
